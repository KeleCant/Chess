package dataAccess;

import java.util.HashMap;
import model.AuthData;
import java.util.UUID;

public class AuthDAOMemory implements AuthDAO {
    private HashMap<String, AuthData> authDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    @Override
    public void clear(){
        authDataList.clear();
    }

    //createAuth: Create a new authorization.
    @Override
    public String createAuth(String username) throws DataAccessException {
        String newAuth = UUID.randomUUID().toString();
        //if by chance the random generator generates the same string twice
        if (authDataList.containsKey(newAuth)) {
            newAuth = UUID.randomUUID().toString();
            //throw new DataAccessException("Error: bad request");
        }
        //place the username and string in the List
        authDataList.put(newAuth, new AuthData(username, newAuth));
        return newAuth;
    }

    //getAuth: Retrieve an authorization given an authToken.
    @Override
    public boolean getAuth(String authToken){
        return authDataList.containsKey(authToken);
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
    @Override
    public void deleteAuth(String authToken) throws DataAccessException{
        if (!authDataList.containsKey(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
        authDataList.remove(authToken);
    }

    @Override
    public String getUsername(String authToken){
        return authDataList.get(authToken).username();
    }

}
