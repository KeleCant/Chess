package dataAccess;

import model.GameData;

import java.util.HashSet;

public interface GameDAO {
    void clear();
    int createGame(String gameName) throws DataAccessException;
    public boolean doesGameExist(int gameID);
    HashSet<GameData> listGame();
    void updateGame(String authToken, int gameID, String clientColor, AuthDAO authDAO) throws DataAccessException;
    void updateGameData(int gameID, GameData game) throws DataAccessException;
}
