package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.userCommands.UserGameCommand;

public class WebsocketServer {
    private Connection playerConnectionList = new Connection();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {

        ////receive message and translate from json // Set up variables
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType commandType = command.getCommandType();

        //create a connection if user joins // Verify Authorization
        if ((commandType == UserGameCommand.CommandType.JOIN_PLAYER) || (commandType == UserGameCommand.CommandType.JOIN_OBSERVER)) {
            playerConnectionList.addConnection(command.getAuthString(), session);
        }

        //Check and run command
        if (commandType == UserGameCommand.CommandType.JOIN_PLAYER){
            joinPlayer();
        } else if (commandType == UserGameCommand.CommandType.JOIN_OBSERVER){
            joinObserver();
        } else if (commandType == UserGameCommand.CommandType.MAKE_MOVE){
            makeMove();
        } else if (commandType == UserGameCommand.CommandType.LEAVE){
            leave();
        } else if (commandType == UserGameCommand.CommandType.RESIGN){
            resign();
        }
    }



    //
    // Websocket end points
    //
    private void joinPlayer(){

    }

    private void joinObserver(){

    }

    private void makeMove(){

    }

    private void leave(){

    }

    private void resign(){

    }
}
