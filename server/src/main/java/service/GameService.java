package service;

import dataAccess.GameDAO;
import dataAccess.GameDAOMemory;
import requests.ListGamesRequest;
import results.ListGamesResult;

public class GameService {

    //Gives a list of all games.
    public ListGamesResult listGames(ListGamesRequest request){
        GameDAO gameDAO = new GameDAOMemory();
        //verify authToken

    }

    //Creates a new game.
    public class createGame {

    }

    //Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game.
    // If no color is specified the user is joined as an observer.
    // This request is idempotent.
    public class joinGameService {

    }
}
