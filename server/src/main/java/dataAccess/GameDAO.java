package dataAccess;

import chess.*;
import model.GameData;
import java.util.HashSet;

public interface GameDAO {
    void clear();
    int createGame(String gameName);
    GameData getGame(int gameID) throws DataAccessException;
    HashSet<GameData> listGame();
    void updateGame(String authToken, int gameID, String ClientColor) throws DataAccessException;

}
