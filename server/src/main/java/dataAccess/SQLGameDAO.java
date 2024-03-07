package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.HashSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

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

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
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
    public int createGame(String gameName) throws DataAccessException {
        var gson = new Gson();
        return executeUpdate("INSERT INTO gameDataTable (whiteusername, blackusername, gamename, game) VALUES (?, ?, ?, ?)", null, null, gameName, gson.toJson(new ChessGame()));
    }
    @Override
    public void updateGame(String authToken, int gameID, String clientColor, AuthDAO authDAO) throws DataAccessException {

//        GameData game = gameDataList.get(gameID);
//
//        if (clientColor == null){
//            //join as spectator
//            game = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
//            gameDataList.put(gameID, game);
//        }
//        else if (clientColor.equals("WHITE")) {   //add White player
//            //check to see if color is taken
//            if (game.whiteUsername() != null)
//                throw new DataAccessException("Error: already taken");
//
//            game = new GameData(game.gameID(), authDAO.getUsername(authToken), game.blackUsername(), game.gameName(), game.game());
//            gameDataList.put(gameID, game);
//        }
//        else if (clientColor.equals("BLACK")) {  //add Black player
//            //check to see if color is taken
//            if (game.blackUsername() != null)
//                throw new DataAccessException("Error: already taken");
//
//            game = new GameData(game.gameID(), game.whiteUsername(), authDAO.getUsername(authToken), game.gameName(), game.game());
//            gameDataList.put(gameID, game);
//        }
//        else {
//            throw new DataAccessException("Error: bad request");
//        }




//        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM authDataTable WHERE authToken=?")) {
//            preparedStatement.setString(1, authToken);
//            try (var resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return true;
//                }
//            }
//        } catch (DataAccessException | SQLException exception) {throw new RuntimeException(exception);}
//        return false;














    }

    /*
    Read Data
     */
    @Override
    public boolean doesGameExist(int gameID) {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM gameDataTable WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (DataAccessException | SQLException exception) { throw new RuntimeException(exception); }
    }
    @Override
    public HashSet<GameData> listGame() {
        HashSet<GameData> gameList = new HashSet<>();
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM gameDataTable")) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    gameList.add(new GameData(resultSet.getInt("gameid"), resultSet.getString("whiteusername"), resultSet.getString("blackusername"), resultSet.getString("gamename"), new Gson().fromJson(resultSet.getString("chessgame"), ChessGame.class)));
                }
                return gameList;
            }
        } catch (DataAccessException | SQLException exception) { throw new RuntimeException(exception); }
    }
}
