package dataAccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO {

    /*
    configuration
     */

    public SQLAuthDAO() throws DataAccessException {
        //configure database
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
            CREATE TABLE IF NOT EXISTS  authDataTable (
                `authtoken` varchar(256) NOT NULL,
                `username` varchar(256) NOT NULL,
                PRIMARY KEY (`authtoken`),
                INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    /*
    Input Data
     */
    @Override
    public void clear() {
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("TRUNCATE authDataTable")) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException exception) {throw new RuntimeException(exception);}
    }
    @Override
    public String createAuth(String username) throws DataAccessException {
        String newAuth = UUID.randomUUID().toString();
        try (var con = DatabaseManager.getConnection()) {
            var statement="INSERT INTO auth (username, authToken) VALUES (?, ?)";
            try (var preparedStatement = con.prepareStatement(statement)) {
                preparedStatement.setString(1, username);   //value ?
                preparedStatement.setString(2, newAuth);    //value ?
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exeption) {throw new RuntimeException(exeption);}
        return newAuth;
    }
    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("DELETE FROM authDataTable WHERE authToken=?")) {
                preparedStatement.setString(1, authToken);  //value ?
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {throw new RuntimeException(exception);}
    }

    /*
    Read Data
     */
    @Override
    public boolean getAuth(String authToken) {
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("SELECT * FROM auth WHERE authToken=?")) {
                preparedStatement.setString(1, authToken);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                       return true;
                    }
                }
            }
        } catch (DataAccessException | SQLException exception) {throw new RuntimeException(exception);}
        return false;
    }
    @Override
    public String getUsername(String authToken) {
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("SELECT * FROM auth WHERE authToken=?")) {
                preparedStatement.setString(1, authToken);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("username");
                    } else {
                        throw new DataAccessException("Error: unauthorized");
                    }
                }
            }
        } catch (DataAccessException | SQLException exception) {throw new RuntimeException(exception);}
    }
}
