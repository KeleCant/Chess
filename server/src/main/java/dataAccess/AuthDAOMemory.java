package dataAccess;

import java.util.HashMap;
import model.AuthData;
import java.util.UUID;

public class AuthDAOMemory implements AuthDAO {
    private HashMap<String, AuthData> AuthDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    @Override
    public void clear(){
        AuthDataList.clear();
    }

    //createAuth: Create a new authorization.
    @Override
    public String createAuth(String username) throws DataAccessException {
        String newAuth = UUID.randomUUID().toString();
        //if by chance the random generator generates the same string twice
        if (AuthDataList.containsKey(newAuth)) {
            newAuth = UUID.randomUUID().toString();
            //throw new DataAccessException("Error: bad request");
        }
        //place the username and string in the List
        AuthDataList.put(newAuth, new AuthData(username, newAuth));
        return newAuth;
    }

    //getAuth: Retrieve an authorization given an authToken.
    @Override
    public boolean getAuth(String authToken){
        return AuthDataList.containsKey(authToken);
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
    @Override
    public void deleteAuth(String authToken) throws DataAccessException{
        if (!AuthDataList.containsKey(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
        AuthDataList.remove(authToken);
    }

    @Override
    public String getUsername(String authToken){
        return AuthDataList.get(authToken).username();
    }

}
