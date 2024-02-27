package dataAccess;

import model.AuthData;
import model.UserData;
import java.util.HashMap;
import java.util.Objects;

public class UserDAOMemory implements UserDAO {
    private HashMap<String, UserData> userDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    @Override
    public void clear(){
        userDataList.clear();
    }

    //createUser: Create a new user.
    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException{
        UserData newUser = new UserData(username, password, email);

        if (userDataList.containsKey(newUser.username())) {
            throw new DataAccessException("Error: already taken");
        }
        userDataList.put(newUser.username(), newUser);
        return newUser;
    }

//    //getUser: Retrieve a user with the given username.
//    @Override
//    public UserData getUser(String username) throws DataAccessException{
//        if (!userDataList.containsKey(username)) {
//            throw new DataAccessException("Error: bad request");
//        }
//        return userDataList.get(username);
//    }
    @Override
    public void checkUsername(String username) throws DataAccessException{
        if (userDataList.containsKey(username)) {
            throw new DataAccessException("Error: already taken");
        }
    }

    @Override
    public void checkPassword(String username, String password) throws DataAccessException{
        //make sure user is in the system
        if (!userDataList.containsKey(username))
            throw new DataAccessException("Error: unauthorized");

        if (!Objects.equals(userDataList.get(username).password(), password)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
