package dataAccess;

import model.GameData;

import java.util.HashSet;

public class SQLGameDAO implements GameDAO {
    @Override
    public void clear() {

    }

    @Override
    public int createGame(String gameName) {
        return 0;
    }

    @Override
    public boolean doesGameExist(int gameID) {
        return false;
    }

    @Override
    public HashSet<GameData> listGame() {
        return null;
    }

    @Override
    public void updateGame(String authToken, int gameID, String clientColor, AuthDAO authDAO) throws DataAccessException {

    }
}
