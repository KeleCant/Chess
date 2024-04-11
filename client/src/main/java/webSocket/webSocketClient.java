package webSocket;

import chess.ChessGame;
import chess.ChessPosition;
import com.google.gson.Gson;
import ui.BoardUI;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

public class webSocketClient extends Endpoint {
    private Session session;
    private BoardUI gameBoard;
    private ChessGame game;
    private String team;


    public webSocketClient(String url, BoardUI gameBoard, String team) throws Exception {
        this.gameBoard = gameBoard;
        this.team = team;

        url = url.replace("http", "ws") + "connect";
        //System.out.println("Connecting to Websocket URL: " + url);

        //session = ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(url));
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, new URI(url));


        session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);

                if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
                    loadGame(message);
                } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
                    ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
                    gameBoard.sendMessage(errorMessage.getErrorMessage());
                } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
                    NotificationMessage notification = new Gson().fromJson(message, NotificationMessage.class);
                    gameBoard.sendMessage(notification.getMessage());
                }
            }
        });
    }

    public void send(String message) throws Exception {
        session.getBasicRemote().sendText(message);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {}
    public void onClose(Session session, EndpointConfig endpointConfig) {}
    public void onError(Session session, EndpointConfig endpointConfig) {}

    private void loadGame(String message) { //redraws game
        LoadGameMessage loadGameResponse = new Gson().fromJson(message, LoadGameMessage.class);
        gameBoard.updateGame(loadGameResponse.getGame());
        System.out.println();
        gameBoard.drawBoard(team);
        System.out.print("\n[LOGGED_IN] >>>> ");
    }

    public void redrawBoard(){
        gameBoard.drawBoard(team);
    }

    public void highlightMoves(ChessPosition pos) {
        gameBoard.highlightMoves(pos, team);
    }
}
