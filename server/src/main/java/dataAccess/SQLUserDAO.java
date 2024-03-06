package dataAccess;

import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class SQLUserDAO implements UserDAO {

    /*
    Configuration
     */

    public SQLUserDAO() throws DataAccessException {
        //Configuration
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS  userDataTable (
                `username` varchar(256) NOT NULL,
                `password` varchar(256) NOT NULL,
                `email` varchar(256) NOT NULL,
                PRIMARY KEY (`username`),
                INDEX(password),
                INDEX(email)
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
    Input data
     */

    @Override
    //clear database
    public void clear() {
        try (var con = DatabaseManager.getConnection(); var preparedStatement = con.prepareStatement("TRUNCATE userDataTable")) {
            preparedStatement.executeUpdate();
        } catch (DataAccessException | SQLException exception) { throw new RuntimeException(exception); }
    }
    @Override
    //insert new user into database
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        try (Connection con = DatabaseManager.getConnection()) {
            //throw exception if username already exists
            try(var preparedStatement = con.prepareStatement("SELECT * FROM userDataTable WHERE username=?")){
                preparedStatement.setString(1, username);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        throw new DataAccessException("Error: already taken");
                    }
                }
            }
            //adds user
            try(var statement = con.prepareStatement("INSERT INTO userDataTable (username, password, email) VALUES (?, ?, ?)")){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String newPassword = encoder.encode(password);
                statement.setString(1, username);
                statement.setString(2, newPassword);
                statement.setString(3, email);
                statement.executeUpdate();
            }
        } catch (SQLException exception) {throw new RuntimeException(exception);}
        return new UserData(username, password, email);
    }


    /*
    Read data
     */
    @Override public void checkUsername(String username) throws DataAccessException {
        try (Connection con = DatabaseManager.getConnection(); var statement = con.prepareStatement("SELECT * FROM userDataTable WHERE username = ?")) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            if (resultSet.next()){
                throw new DataAccessException("Error: already taken");
            }
        } catch (SQLException exception) {throw new RuntimeException(exception);}
    }
    @Override public void checkPassword(String username, String password) throws DataAccessException {
        try (Connection con = DatabaseManager.getConnection(); var statement = con.prepareStatement("SELECT * FROM userDataTable WHERE username = ?")) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var realPassword = resultSet.getString("password");
                if (!Objects.equals(realPassword, password))
                    throw new DataAccessException("Error: unauthorized");
            } else {
                //if no username was found
                throw new DataAccessException("Error: unauthorized");
            }
        } catch (SQLException exception) { throw new RuntimeException(exception); }
    }
    @Override public HashMap<String, UserData> getMap() {
        return null;
    }
}
