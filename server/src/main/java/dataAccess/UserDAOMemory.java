package dataAccess;

import model.AuthData;
import model.UserData;
import java.util.HashMap;
import java.util.Objects;

public class UserDAOMemory implements UserDAO {
    private HashMap<String, UserData> UserDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    @Override
    public void clear(){
        UserDataList.clear();
    }

    //createUser: Create a new user.
    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException{
        UserData newUser = new UserData(username, password, email);

        if (UserDataList.containsKey(newUser.username())) {
            throw new DataAccessException("Exit Code 403 \"Error: already taken\"");
        }
        UserDataList.put(newUser.username(), newUser);
        return newUser;
    }

    //getUser: Retrieve a user with the given username.
    @Override
    public UserData getUser(String username) throws DataAccessException{
        if (!UserDataList.containsKey(username)) {
            throw new DataAccessException("Exit Code 400 \"Error: bad request\"");
        }
        return UserDataList.get(username);
    }
    @Override
    public void checkUsername(String username) throws DataAccessException{
        if (!UserDataList.containsKey(username)) {
            throw new DataAccessException("Exit Code 403 \"Error: already taken\"");
        }
    }

    @Override
    public void checkPassword(String username, String password) throws DataAccessException{
        if (!Objects.equals(UserDataList.get(username).password(), password)) {
            throw new DataAccessException("Exit Code 401 \"Error: unauthorized\"");
        }
    }
}
