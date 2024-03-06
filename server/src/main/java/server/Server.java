package server;

import com.google.gson.Gson;
import dataAccess.*;
import requests.*;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Server {
    //for local Storage
    private final AuthDAO authDAO = new AuthDAOMemory();
    private final GameDAO gameDAO = new GameDAOMemory();
    private final UserDAO userDAO = new UserDAOMemory();

    //for sequal storage
//    private AuthDAO authDAO;
//    private GameDAO gameDAO;
//    private UserDAO userDAO;
//    public Server() {
//        try {
//            this.authDAO = new SQLAuthDAO();
//            this.gameDAO = new SQLGameDAO();
//            this.userDAO = new SQLUserDAO();
//        } catch (Exception ex) {
//            System.out.printf("Unable to start server: %s%n", ex.getMessage());
//        }
//    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", this::clearHandler);
        Spark.post("/user", this::registerHandler);
        Spark.post("/session", this::loginHandler);
        Spark.delete("/session", this::logoutHandler);
        Spark.get("/game", this::listGamesHandler);
        Spark.post("/game", this::createGameHandler);
        Spark.put("/game", this::joinGameHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearHandler (Request req, Response res) {
        ClearService.clear(authDAO, gameDAO, userDAO);
        res.status(200);
        return new Gson().toJson(new JsonErrorMessage(""));
    }

    private Object registerHandler (Request req, Response res){
        try {
            RegistrationRequest request = new Gson().fromJson(req.body(), RegistrationRequest.class);
            UserService userService = new UserService(authDAO, userDAO);
            Object object = userService.register(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object loginHandler (Request req, Response res){
        try {
            LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
            UserService userService = new UserService(authDAO, userDAO);
            Object object = userService.login(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object logoutHandler (Request req, Response res){
        try {
            //LogoutRequest request = new Gson().fromJson(req.body(), LogoutRequest.class);
            LogoutRequest request = new LogoutRequest(req.headers("authorization"));
            UserService userService = new UserService(authDAO, userDAO);
            Object object = userService.logout(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object listGamesHandler (Request req, Response res){
        try {
            //LogoutRequest request = new Gson().fromJson(req.body(), LogoutRequest.class);
            ListGamesRequest request = new ListGamesRequest(req.headers("authorization"));
            GameService gameService = new GameService(authDAO, gameDAO);
            Object object = gameService.listGamesService(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object createGameHandler (Request req, Response res){
        try {
            CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
            request.setAuthToken(req.headers("authorization"));
            GameService gameService = new GameService(authDAO, gameDAO);
            Object object = gameService.createGameService(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object joinGameHandler (Request req, Response res){
        try {
            JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
            String authToken = req.headers("authorization");
            GameService gameService = new GameService(authDAO, gameDAO);
            Object object = gameService.joinGameService(request, authToken);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private int returnErrorType (String caughtErrorMessage){
        if (caughtErrorMessage == "Error: bad request")
            return 400;
        else if (caughtErrorMessage == "Error: unauthorized")
            return 401;
        else if (caughtErrorMessage == "Error: already taken")
            return 403;
        else
            return 500;
    }
}