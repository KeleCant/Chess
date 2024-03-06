package dataAccess;

import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

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

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    //else if (param instanceof  p) ps.setString(i + 1, p.toString());
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
    Input data
     */

    @Override
    //clear database
    public void clear() {
//        try (var con = DatabaseManager.getConnection()) {
//            try (var preparedStatement = con.prepareStatement("TRUNCATE userDataTable")) {
//                preparedStatement.executeUpdate();
//            }
//        } catch (DataAccessException | SQLException exception) {
//            throw new RuntimeException(exception);
//        }
        var statement = "TRUNCATE user";
        try {
            executeUpdate(statement);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    //insert new user into database
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        try (Connection con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?)")) {
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
        try (Connection con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement("SELECT * FROM user WHERE username = ?")) {
                statement.setString(1, username);
                var resultSet = statement.executeQuery();
                if(resultSet.next()){
                    throw new DataAccessException("Error: already taken");
                }
            }
        } catch (SQLException exception) {throw new RuntimeException(exception);}
    }
    @Override public void checkPassword(String username, String password) throws DataAccessException {

    }
    @Override public HashMap<String, UserData> getMap() {
        var map = new HashMap<String, UserData>();
        try (var con = DatabaseManager.getConnection()) {
            try (var ps = con.prepareStatement("SELECT username, password, email FROM user")) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        //map.put(rs.getString("username"), readUser(rs));
                    }
                }
            }
        } catch (Exception exception) { throw new RuntimeException(exception); }
        return map;
    }
}
