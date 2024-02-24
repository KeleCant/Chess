package dataAccess;

import model.GameData;
import java.util.HashSet;
import java.util.HashMap;

public class GameDAOMemory implements GameDAO {
    private HashMap<int, GameData> GameDataList = new HashMap<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    @Override
    public void clear(){
        GameDataList.clear();
    }

    //createGame: Create a new game.
    @Override
    public int createGame(GameData gameData){

    }

    //getGame: Retrieve a specified game with the given game ID.
    @Override
    public GameData getGame(int gameID) throws DataAccessException{

    }

    //listGames: Retrieve all games.
    @Override
    public HashSet<GameData> listGame(){

    }

    //updateGame: Updates a chess game.
    //It should replace the chess game string corresponding to a given gameID.
    //This is used when players join a game or when a move is made.
    @Override
    public void updateGame(){

    }
}
