package dataAccess;

import chess.*;
import model.*;
import java.util.HashSet;
import java.util.HashMap;


public class GameDAOMemory implements GameDAO {
    private HashMap<Integer, GameData> gameDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    @Override
    public void clear(){
        gameDataList.clear();
    }

    //createGame: Create a new game.
    @Override
    public int createGame(String gameName){
        int gameID = gameDataList.size()+1;
        gameDataList.put(gameID, new GameData(gameID, null, null, gameName, new ChessGame()));
        return gameID;
    }

    //getGame: Retrieve a specified game with the given game ID.
    @Override
    public GameData getGame(int gameID) throws DataAccessException{
        //check to make sure this game ID exits
        if (!gameDataList.containsKey(gameID)){
            throw new DataAccessException("Error: bad request");
        }
        return gameDataList.get(gameID);
    }

    //listGames: Retrieve all games.
    @Override
    public HashSet<GameData> listGame(){
        return new HashSet<GameData>(gameDataList.values());
    }

    //updateGame: Updates a chess game.
    //It should replace the chess game string corresponding to a given gameID.
    //This is used when players join a game or when a move is made. fixme this does not apply to new moves made
    @Override
    public void updateGame(String authToken, int gameID, String clientColor) throws DataAccessException {
        AuthDAO authDAO = new AuthDAOMemory();
        GameData updateGameData = gameDataList.get(gameID);

        if (clientColor == "WHITE") {   //add White player
            //check to see if color is taken
            if (updateGameData.whiteUsername() != null){
                throw new DataAccessException("Error: already taken");
            }
            gameDataList.put(gameID, new GameData(
                    gameID,
                    authDAO.getUsername(authToken),
                    updateGameData.blackUsername(),
                    updateGameData.gameName(),
                    updateGameData.game()));
        }
        else if (clientColor == "BLACK") {  //add Black player
            //check to see if color is taken
            if (updateGameData.blackUsername() != null){
                throw new DataAccessException("Error: already taken");
            }
            gameDataList.put(gameID, new GameData(
                    gameID,
                    updateGameData.whiteUsername(),
                    authDAO.getUsername(authToken),
                    updateGameData.gameName(),
                    updateGameData.game()));
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
    }
}
