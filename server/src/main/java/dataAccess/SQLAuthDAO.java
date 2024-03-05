package dataAccess;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() {

    }

    @Override
    public String createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public boolean getAuth(String authToken) {
        return false;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public String getUsername(String authToken) {
        return null;
    }
}
