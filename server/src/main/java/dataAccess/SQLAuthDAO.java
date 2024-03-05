package dataAccess;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {

    /*
    configuration
     */

    public SQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  pet (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `type` ENUM('CAT', 'DOG', 'FISH', 'FROG', 'ROCK') DEFAULT 'CAT',
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(type),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    /*
    Input Data
     */
    @Override
    public void clear() {

    }
    @Override
    public String createAuth(String username) throws DataAccessException {
        return null;
    }
    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    /*
    Read Data
     */
    @Override
    public boolean getAuth(String authToken) {
        return false;
    }
    @Override
    public String getUsername(String authToken) {
        return null;
    }
}
