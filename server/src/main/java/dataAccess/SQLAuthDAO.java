package dataAccess;

import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    /*
    configuration
     */

    public SQLAuthDAO() throws DataAccessException {
        //configure database
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS  authDataTable (
                `authtoken` varchar(256) NOT NULL,
                `username` varchar(256) NOT NULL,
                PRIMARY KEY (`authtoken`),
                INDEX(username)
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
    @Override
    public void clear() {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("TRUNCATE authDataTable")) {
            preparedStatement.executeUpdate();
        } catch (DataAccessException | SQLException exception) {throw new RuntimeException(exception);}
    }
    @Override
    public String createAuth(String username) throws DataAccessException {
//        //check to see if the user already has an auth
//        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM authDataTable WHERE username=?")) {
//            preparedStatement.setString(1, username);
//            try (var resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    throw new DataAccessException("Error: unauthorized");
//                }
//            }
//        } catch (DataAccessException | SQLException exception) {throw new DataAccessException("Error: Already Logged In");}


        String newAuth = UUID.randomUUID().toString();
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("INSERT INTO authDataTable (username, authToken) VALUES (?, ?)")) {
            preparedStatement.setString(1, username);   //value ?
            preparedStatement.setString(2, newAuth);    //value ?
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {throw new RuntimeException(exception);}
        return newAuth;
    }
    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("DELETE FROM authDataTable WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);  //value ?
            int executed = preparedStatement.executeUpdate();
            if (executed == 0)
                throw new DataAccessException("Error: unauthorized");
        } catch (SQLException exception) {throw new RuntimeException(exception);}
    }

    /*
    Read Data
     */
    @Override
    public boolean getAuth(String authToken) {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM authDataTable WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (DataAccessException | SQLException exception) {throw new RuntimeException(exception);}
        return false;
    }
    @Override
    public String getUsername(String authToken) throws DataAccessException {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("SELECT * FROM authDataTable WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                } else {
                    throw new DataAccessException("Error: unauthorized");
                }
            }
        } catch (DataAccessException | SQLException exception) {throw new DataAccessException("Error: unauthorized");}
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "SQLAuthDAO{}";
    }
}
