package dataAccess;

import java.util.HashMap;
import model.AuthData;
import java.util.UUID;

public class AuthDAOMemory implements AuthDAO {
    private HashMap<String, AuthData> AuthDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear(){
        AuthDataList.clear();
    }

    //createAuth: Create a new authorization.
    public String createAuth(String username){
        String newAuth = UUID.randomUUID().toString();
        //if by chance the random generator generates the same string twice
        if (AuthDataList.containsKey(newAuth)) {
            newAuth = UUID.randomUUID().toString();
        }
        //place the username and string in the List
        AuthDataList.put(newAuth, new AuthData(username, newAuth));
        return newAuth;
    }

    //getAuth: Retrieve an authorization given an authToken.
    public boolean getAuth(String authToken){
        return AuthDataList.containsKey(authToken);
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
    public void deleteAuth(String authToken) throws DataAccessException{
        if (!AuthDataList.containsKey(authToken)) {
            throw new DataAccessException("Exit Code 401 \"Error: unauthorized\"");
        }
        AuthDataList.remove(authToken);
    }

}
