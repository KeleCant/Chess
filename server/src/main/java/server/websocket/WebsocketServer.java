package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.userCommands.UserGameCommand;

public class WebsocketServer {
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {

        //receive message and translate from json
        UserGameCommand.CommandType commandType = null;

        //Verify Authorization


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
