package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(8080);

        Spark.staticFiles.location("web");
        //Spark.externalStaticFileLocation("C:\\Users\\kelec\\CS 240\\chess\\web");
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) -> (new ClearHandler()).clear(req, res));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}