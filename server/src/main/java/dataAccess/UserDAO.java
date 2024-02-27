package dataAccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear();
    UserData createUser(String username, String password, String email) throws DataAccessException;
    public void checkUsername(String username) throws DataAccessException;
    public void checkPassword(String username, String password) throws DataAccessException;
}
