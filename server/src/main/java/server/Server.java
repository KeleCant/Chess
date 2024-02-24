package server;

import dataAccess.*;
import spark.*;

public class Server {
    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public int run(int desiredPort) {
        userDAO = new UserDAOMemory();
        authDAO = new AuthDAOMemory();
        gameDAO = new GameDAOMemory();
        Spark.port(8080);

        Spark.staticFiles.location("web");
        //Spark.externalStaticFileLocation("C:\\Users\\kelec\\CS 240\\chess\\web");
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) -> (new ClearHandler()).clear(req, res));
        //register(username, password, email)
        //login(username, password)
        //logout(authToken)
        //listGames(authToken)
        //createGame(gameName)
        //joinGame(ClientColor, gameID)
        //clearApplication()

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}