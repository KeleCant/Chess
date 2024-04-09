package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import service.GameService;
import service.UserService;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Objects;

public class WebsocketServer {
    private final Connection playerConnectionList = new Connection();
    private AuthDAO authDAO = new SQLAuthDAO();
    private GameDAO gameDAO = new SQLGameDAO();
    private UserDAO userDAO = new SQLUserDAO();
    private final GameService gameService = new GameService(authDAO, gameDAO);
    private final UserService userService = new UserService(authDAO, userDAO);

    public WebsocketServer() throws DataAccessException {}

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {

        ////receive message and translate from json // Set up variables
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType commandType = command.getCommandType();

        //create a connection if user joins // Verify Authorization
        if ((commandType == UserGameCommand.CommandType.JOIN_PLAYER) || (commandType == UserGameCommand.CommandType.JOIN_OBSERVER)) {
            playerConnectionList.addConnection(command.getAuthString(), session);
        }

        //Creates a pointer to this new connection
        Session connection = playerConnectionList.getSession(command.getAuthString());

        //Check and run command
        if (commandType == UserGameCommand.CommandType.JOIN_PLAYER){
            joinPlayer(connection, message);
        } else if (commandType == UserGameCommand.CommandType.JOIN_OBSERVER){
            joinObserver(connection, message);
        } else if (commandType == UserGameCommand.CommandType.MAKE_MOVE){
            makeMove(connection, message);
        } else if (commandType == UserGameCommand.CommandType.LEAVE){
            leave(connection, message);
        } else if (commandType == UserGameCommand.CommandType.RESIGN){
            resign(connection, message);
        }
    }



    //
    // Websocket end points
    //
    private void joinPlayer(Session session, String message){
        //translate String from gson
        JoinPlayerMessage joinMessage = new Gson().fromJson(message, JoinPlayerMessage.class);

        //Use service to join
        try {
            //Check to see if the user can login to this game
            //if empty Yes
            //if username match Yes
            //if username doesn't match No

            //Retrieve GameList
            GameData gameData;
            for (GameData game : gameDAO.listGame())
                if (game.gameID() == joinMessage.getGameID())
                     gameData = game;

            //Retrieve Username
            joinMessage.getAuthString();


            //Check to see if the position is empty

            //Check to see if this user occupies the Color

            if ((joinMessage.getColor() == ChessGame.TeamColor.WHITE && !Objects.equals(authData.username(), gameData.whiteUsername()))
                    || (joinMessage.getColor() == ChessGame.TeamColor.BLACK && !Objects.equals(authData.username(), gameData.blackUsername()))
                    || joinMessage.getColor() == null) {
                ErrorMessage errorResponse = new ErrorMessage("That team slot is already taken");
                session.getRemote().sendString(new Gson().toJson(errorResponse));
                return;
            }

            playerConnectionList.addUser(joinMessage.getGameID(), joinMessage.getAuthString());
            LoadGameMessage loadGameResponse = new LoadGameMessage(gameData.game());
            String msgToSend = new Gson().toJson(loadGameResponse);

            session.getRemote().sendString(msgToSend);

            String broadcastMessage = String.format("%s joined the game as %s", authData.username(), joinMessage.getColor());
            playerConnectionList.broadcast(broadcastMessage, joinMessage.getAuthString(), joinMessage.getGameID());

        } catch (DataAccessException e) {
            ErrorMessage errorResponse = new ErrorMessage("Error");
            session.getRemote().sendString(new Gson().toJson(errorResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void joinObserver(Session session, String message){

    }

    private void makeMove(Session session, String message){

    }

    private void leave(Session session, String message){

    }

    private void resign(Session session, String message){

    }
}
