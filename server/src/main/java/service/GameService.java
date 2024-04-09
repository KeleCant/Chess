package service;

import dataAccess.*;
import requests.*;
import results.*;

public class GameService {

    private static AuthDAO authDAO;
    private static GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public AuthDAO getAuthDAO(){
        return authDAO;
    }

    public GameDAO getGameDAO(){
        return gameDAO;
    }
 
    //Gives a list of all games.
    public static ListGamesResult listGamesService(ListGamesRequest request) throws DataAccessException {
        //verify authToken
        if (!authDAO.getAuth(request.authToken()))
            throw new DataAccessException("Error: unauthorized");

        return new ListGamesResult(gameDAO.listGame());
    }

    //Creates a new game.
    public static CreateGameResult createGameService(CreateGameRequest request) throws DataAccessException{
        //verify authToken
        if (!authDAO.getAuth(request.getAuthToken()))
                throw new DataAccessException("Error: unauthorized");

        //creates game
        return new CreateGameResult(gameDAO.createGame(request.getGameName()));
    }

    //Verifies that the specified game exists, and, if a color is specified, adds the caller as the requested color to the game.
    // If no color is specified the user is joined as an observer.
    // This request is idempotent.
    public static JoinGameResult joinGameService(JoinGameRequest request, String authToken) throws DataAccessException {
        //check auth data
        if (!authDAO.getAuth(authToken))
            throw new DataAccessException("Error: unauthorized");
        //make sure game exists
        if (!gameDAO.doesGameExist(request.gameID()))
            throw new DataAccessException("Error: bad request");

        //intert user
        gameDAO.updateGame(authToken,request.gameID(),request.playerColor(), authDAO);
        return new JoinGameResult();
    }
}
