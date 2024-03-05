package dataAccess;

import model.UserData;

import com.google.gson.Gson;
import java.sql.*;
import java.util.HashMap;

public class SQLUserDAO implements UserDAO {

    /*
    Configuration
     */

    public SQLUserDAO() throws DataAccessException {
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
    Input data
     */

    @Override
    //clear database
    public void clear() {

    }
    @Override
    //incert new user into database
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        return null;
    }


    /*
    Read data
     */
    @Override public void checkUsername(String username) throws DataAccessException {}
    @Override public void checkPassword(String username, String password) throws DataAccessException {}
    @Override public HashMap<String, UserData> getMap() {return null;}
}