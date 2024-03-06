package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.HashSet;

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
              `gamename` VARCHAR(256) NOT NULL,
              'chessgame' JSON NOT NULL,
              PRIMARY KEY (`gameid`)
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
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("TRUNCATE gameDataTable")) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    //greates and imports new game into database
    @Override
    public int createGame(String gameName) {
        return 0;
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
        return null;
    }
}
