package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.ClearService;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;
import requests.*;
import results.*;
import dataAccess.*;

public class Server {
    private final AuthDAOMemory authDAO;
    private final GameDAOMemory gameDAO;
    private final UserDAOMemory userDAO;


    public Server(){
        this.authDAO = new AuthDAOMemory();
        this.gameDAO = new GameDAOMemory();
        this.userDAO = new UserDAOMemory();
    }


//    public static void main(String[] args){
//        try{
//            int port = Integer.parseInt(args[0]);
//            Server temp = new Server();
//            temp.run(port);
//        }
//        catch (Exception exp){
//            System.err.println(exp.getMessage());
//        }
//    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", this::ClearHandler);
        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::LoginHandler);
        Spark.delete("/session", this::LogoutHandler);
        Spark.get("/game", this::ListGamesHandler);
        Spark.post("/game", this::CreateGameHandler);
        Spark.put("/game", this::JoinGameHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object ClearHandler (Request req, Response res) {
        ClearService.clear();
        res.status(200);
        return new Gson().toJson(new JsonErrorMessage(""));
    }

    private Object RegisterHandler (Request req, Response res){
        try {
            RegistrationRequest request = new Gson().fromJson(req.body(), RegistrationRequest.class);
            Object object = UserService.register(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object LoginHandler (Request req, Response res){
        try {
            LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
            Object object = UserService.login(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object LogoutHandler (Request req, Response res){
        try {
            //LogoutRequest request = new Gson().fromJson(req.body(), LogoutRequest.class);
            LogoutRequest request = new LogoutRequest(req.headers("authorization"));
            Object object = UserService.logout(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object ListGamesHandler (Request req, Response res){
        try {
            //LogoutRequest request = new Gson().fromJson(req.body(), LogoutRequest.class);
            ListGamesRequest request = new ListGamesRequest(req.headers("authorization"));
            Object object = GameService.listGamesService(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object CreateGameHandler (Request req, Response res){
        try {
            CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
            request.setAuthToken(req.headers("authorization"));
            Object object = GameService.createGameService(request);
            res.status(200);
            return new Gson().toJson(object);
        }
        catch (DataAccessException exeption){
            res.status(returnErrorType(exeption.getMessage()));
            return new Gson().toJson(new JsonErrorMessage(exeption.getMessage()));
        }
    }

    private Object JoinGameHandler (Request req, Response res){
        try {
            JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
            request.setAuthToken(req.headers("authorization"));
            Object object = GameService.joinGameService(request);
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