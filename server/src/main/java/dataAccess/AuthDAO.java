package dataAccess;

public interface AuthDAO {
    void clear();
    String createAuth(String username);
    boolean getAuth(String authToken);
    void deleteAuth(String authToken) throws DataAccessException;
}
