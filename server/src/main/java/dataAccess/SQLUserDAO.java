package dataAccess;

import model.UserData;

import com.google.gson.Gson;
import java.sql.*;
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
    Input data
     */

    @Override
    //clear database
    public void clear() {
        try (var con = DatabaseManager.getConnection()) {
            try (var preparedStatement = con.prepareStatement("TRUNCATE userDataTable")) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException exception) {
            throw new RuntimeException(exception);
        }
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
