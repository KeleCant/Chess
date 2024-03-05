package dataAccess;

import model.UserData;

import java.util.HashMap;

public class SQLUserDAO implements UserDAO {
    @Override
    public void clear() {

    }

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        return null;
    }

    @Override
    public void checkUsername(String username) throws DataAccessException {

    }

    @Override
    public void checkPassword(String username, String password) throws DataAccessException {

    }

    @Override
    public HashMap<String, UserData> getMap() {
        return null;
    }
}
