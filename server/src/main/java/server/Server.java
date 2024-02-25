package server;

import dataAccess.*;
import spark.*;
import service.*;
import model.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.delete("/db", this::Clear);
        Spark.post("/user", this::Register);
        Spark.post("/session", this::Login);
        Spark.delete("/session", this::Logout);
        Spark.get("/game", this::ListGames);
        Spark.post("/game", this::CreateGame);
        Spark.put("/game", this::JoinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object Clear(Request req, Response res) {
        try{
            ClearService.clear();

        }
        catch (DataAccessException exception){

        }
    }

    private Object Register(Request req, Response res){

    }

    private Object Login(Request req, Response res){

    }

    private Object Logout(Request req, Response res){

    }

    private Object ListGames(Request req, Response res){

    }

    private Object CreateGame(Request req, Response res){

    }

    private Object JoinGame(Request req, Response res){

    }
}