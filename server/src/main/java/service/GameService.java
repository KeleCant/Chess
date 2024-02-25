package service;

import dataAccess.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import results.CreateGameResult;
import results.ListGamesResult;

public class GameService {

    //Gives a list of all games.
    public ListGamesResult listGames(ListGamesRequest request) throws DataAccessException {
        GameDAO gameDAO = new GameDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        //verify authToken
        if (!authDAO.getAuth(request.authToken()))
            throw new DataAccessException("Exit Code 401 \"Error: unauthorized\"");

        return new ListGamesResult(gameDAO.listGame());
    }

    //Creates a new game.
    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException{
        GameDAO gameDAO = new GameDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        //verify authToken
        if (!authDAO.getAuth(request.authToken()))
                throw new DataAccessException("Exit Code 401 \"Error: unauthorized\"");

        //creates game
        return new CreateGameResult(gameDAO.createGame(request.gameName()));
    }

    //Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game.
    // If no color is specified the user is joined as an observer.
    // This request is idempotent.
    public void joinGameService(JoinGameRequest request) throws DataAccessException {
        GameDAO gameDAO = new GameDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        //check auth data
        if (!authDAO.getAuth(request.authToken()))
            throw new DataAccessException("Exit Code 401 \"Error: unauthorized\"");

        //intert user
        gameDAO.updateGame(request.authToken(),request.gameID(),request.ClientColor());
    }
}
