package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.HashSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLGameDAO implements GameDAO {

    /*
    Configuration
     */

    public SQLGameDAO() throws DataAccessException {
        //configure database
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS gameDataTable (
              `gameid` int NOT NULL AUTO_INCREMENT,
              `whiteusername` varchar(256),
              `blackusername` varchar(256),
              `gamename` varchar(256),
              `chessgame` JSON NOT NULL,
              PRIMARY KEY (`gameid`),
              INDEX(gamename)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            };
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    /*
    Input Data
     */
    //deletes all data in the database
    @Override
    public void clear() {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("TRUNCATE gameDataTable")) {
            preparedStatement.executeUpdate();
        } catch (DataAccessException | SQLException exception) { throw new RuntimeException(exception); }
    }
    //creates and imports new game into database
    @Override
    public int createGame(String gameName) {
        int gameID;
        ChessGame chessGame = new ChessGame();
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("INSERT INTO game (gameName, whiteUsername, blackUsername, game) VALUES (?, ?, ?, ?)", RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
                preparedStatement.setObject(4, new Gson().toJson(chessGame));
                preparedStatement.executeUpdate();
                try (var rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        // Retrieve the auto-increment key
                        gameID = rs.getInt(1);
                    } else {
                        throw new SQLException("No auto-generated keys were returned.");
                    }
                }
            }
        } catch (DataAccessException | SQLException exception) { throw new RuntimeException(exception); }
        return gameID;
    }
    @Override
    public void updateGame(String authToken, int gameID, String clientColor, AuthDAO authDAO) throws DataAccessException {

    }

    /*
    Read Data
     */
    @Override
    public boolean doesGameExist(int gameID) {
        return false;
    }
    @Override
    public HashSet<GameData> listGame() {
        HashSet<GameData> games = new HashSet<>();
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM gameDataTable")) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(new GameData(resultSet.getInt("gameid"), resultSet.getString("whiteusername"), resultSet.getString("blackusername"), resultSet.getString("gamename"), new Gson().fromJson(resultSet.getString("chessgame"), ChessGame.class)));
                }
                return games;
            }
        } catch (DataAccessException | SQLException exception) { throw new RuntimeException(exception); }
    }
}
