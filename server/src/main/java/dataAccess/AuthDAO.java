package dataAccess;

public interface AuthDAO {
    void clear();
    String createAuth(String username) throws DataAccessException;
    boolean getAuth(String authToken);
    void deleteAuth(String authToken) throws DataAccessException;
    public String getUsername(String authToken) throws DataAccessException;
}
