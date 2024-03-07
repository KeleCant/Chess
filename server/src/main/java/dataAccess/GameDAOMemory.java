package dataAccess;

import chess.*;
import model.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Objects;


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

    @Override
    public boolean doesGameExist(int gameID) {
        //check to make sure this game ID exits
        if (gameDataList.containsKey(gameID)){
            return true;
        }
        return false;
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
    public void updateGame(String authToken, int gameID, String clientColor, AuthDAO authDAO) throws DataAccessException {
        GameData game = gameDataList.get(gameID);

        if (clientColor == null){
            //join as spectator
            game = new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
            gameDataList.put(gameID, game);
        }
        else if (clientColor.equals("WHITE")) {   //add White player
            //check to see if color is taken
            if (game.whiteUsername() != null)
                throw new DataAccessException("Error: already taken");

            game = new GameData(game.gameID(), authDAO.getUsername(authToken), game.blackUsername(), game.gameName(), game.game());
            gameDataList.put(gameID, game);
        }
        else if (clientColor.equals("BLACK")) {  //add Black player
            //check to see if color is taken
            if (game.blackUsername() != null)
                throw new DataAccessException("Error: already taken");

            game = new GameData(game.gameID(), game.whiteUsername(), authDAO.getUsername(authToken), game.gameName(), game.game());
            gameDataList.put(gameID, game);
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
    }

    public HashMap<Integer, GameData> getMap() {
        return gameDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDAOMemory that = (GameDAOMemory) o;
        return Objects.equals(gameDataList, that.gameDataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameDataList);
    }

    public void updateGameData(int gameID, GameData game) throws DataAccessException{

    }
}
