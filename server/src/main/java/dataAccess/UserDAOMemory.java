package dataAccess;

import model.*;
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
        HashMap<String, UserData> t = getMap();
    }

    @Override
    public HashMap<String, UserData> getMap (){
        return userDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDAOMemory that = (UserDAOMemory) o;
        return Objects.equals(userDataList, that.userDataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDataList);
    }
}
