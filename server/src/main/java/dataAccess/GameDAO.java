package dataAccess;

import model.*;

import java.util.HashSet;

public interface GameDAO {
    void clear();
    int createGame(String gameName);
    public boolean doesGameExist(int gameID);
    HashSet<GameData> listGame();
    void updateGame(String authToken, int gameID, String clientColor, AuthDAO authDAO) throws DataAccessException;

}
