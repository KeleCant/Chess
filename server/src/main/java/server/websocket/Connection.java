package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Connection {
    public static Map<String, Session> userConnectionList = new HashMap<>();  //String 1 is an AuthToken, key for the Session
    public static Map<Integer, Set<String>> gameList = new HashMap<>();  //the int is the game ID // the Set<String> is a list of authTokens with the users present

    //
    //  userConnectionList
    //
    public Session getSession(String authToken) {
        return userConnectionList.get(authToken);
    }

    public void addConnection(String authToken, Session session) {
        userConnectionList.put(authToken, session);
    }

    public void removeConnection(String authToken) {
        userConnectionList.remove(authToken);
    }

    //
    //  gameList
    //
    public Set<String> getUsers(int gameID) {
        return gameList.get(gameID);
    }

    public Map<Integer, Set<String>> getGameList() {
        return gameList;
    }

    public void addUser(int gameID, String authToken) {
        gameList.computeIfAbsent(gameID, k -> new HashSet<>());
        gameList.get(gameID).add(authToken);
    }

    public void removeUser(String authToken, int gameID){
        gameList.get(gameID).remove(authToken);
        userConnectionList.remove(authToken);
    }


    //
    //Message Sending Commands
    //
    public void updateGameBoard(int gameID, ChessGame game) throws IOException {

        //Creates gson message to send to users
        String gsonMessage = new Gson().toJson(new LoadGameMessage(game));

        //sends the new board to each user
        for (String user: getUsers(gameID))
            if (getSession(user).isOpen())
                getSession(user).getRemote().sendString(gsonMessage);

    }

    public void broadcast(String message, String authToken, int gameID) throws DataAccessException, IOException {

        //Creates gson Message
        String gsonMessage = new Gson().toJson(new NotificationMessage(message));

        //Updates every other User
        for (String user: getUsers(gameID))
            if (!user.equals(authToken))
                if (getSession(user).isOpen())
                    getSession(user).getRemote().sendString(gsonMessage);
    }
}