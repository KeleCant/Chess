package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.ClearService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

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

    private Object Clear(Request request, Response response) {
        try{
            ClearService.clear();
            response.type("application/json");
            response.status(200);
        }
        catch (DataAccessException exception){
            response.type("application/json");
            response.status(500);
            return new Gson().toJson(Map.of("message", exception.getMessage()));
        }
    }

    private Object Register(Request request, Response response){
        try {
            UserService.register(request);
        } catch (DataAccessException exception){

        } catch (Exception exception){
            response.type("application/json");
            response.status(500);
            return new Gson().toJson(Map.of("message", exception.getMessage()));
        }
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