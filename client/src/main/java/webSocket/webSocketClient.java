package webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import ui.BoardUI;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

public class webSocketClient {
    private Session session;
    private BoardUI gameBoard;
    private ChessGame game;
    private String team;


    public webSocketClient(String url, BoardUI gameBoard, String team) throws Exception {
        this.gameBoard = gameBoard;
        this.team = team;

        url = url.replace("http", "ws") + "connect";
        System.out.println(url);
        URI uri = new URI(url);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, uri);

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



    private void loadGame(String message) { //redraws game
        LoadGameMessage loadGameResponse = new Gson().fromJson(message, LoadGameMessage.class);

        loadGameResponse.getGame();
    }

    public void redrawBoard(){
        gameBoard.drawBoard(team);
    }


    public void send(String msg) throws Exception {this.session.getBasicRemote().sendText(msg);}
    public void onOpen(Session session, EndpointConfig endpointConfig) {}
    public void onClose(Session session, EndpointConfig endpointConfig) {}
    public void onError(Session session, EndpointConfig endpointConfig) {}



    public void highlightMoves() {
        gameBoard.highlightMoves();
    }
}
