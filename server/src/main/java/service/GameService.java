package service;

import dataAccess.*;
import requests.*;
import results.*;

public class GameService {

    //Gives a list of all games.
    public static ListGamesResult listGamesService(ListGamesRequest request) throws DataAccessException {
        GameDAO gameDAO = new GameDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        //verify authToken
        if (!authDAO.getAuth(request.authToken()))
            throw new DataAccessException("Error: unauthorized");

        return new ListGamesResult(gameDAO.listGame());
    }

    //Creates a new game.
    public static CreateGameResult createGameService(CreateGameRequest request) throws DataAccessException{
        GameDAO gameDAO = new GameDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        //verify authToken
        if (!authDAO.getAuth(request.getAuthToken()))
                throw new DataAccessException("Error: unauthorized");

        //creates game
        return new CreateGameResult(gameDAO.createGame(request.getGameName()));
    }

    //Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game.
    // If no color is specified the user is joined as an observer.
    // This request is idempotent.
    public static JoinGameResult joinGameService(JoinGameRequest request) throws DataAccessException {
        GameDAO gameDAO = new GameDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        //check auth data
        if (!authDAO.getAuth(request.AuthToken()))
            throw new DataAccessException("Error: unauthorized");

        //intert user
        gameDAO.updateGame(request.AuthToken(),request.gameID(),request.ClientColor());
        return new JoinGameResult();
    }
}
