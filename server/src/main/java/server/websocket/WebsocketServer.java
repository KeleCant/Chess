package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.*;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import service.UserService;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.userCommands.JoinObserverMessage;
import webSocketMessages.userCommands.JoinPlayerMessage;
import webSocketMessages.userCommands.MakeMoveMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Objects;

@WebSocket
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

        //receive message and translate from json // Set up variables
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
    private void joinPlayer(Session session, String message) throws IOException {
        //translate String from gson
        JoinPlayerMessage joinMessage = new Gson().fromJson(message, JoinPlayerMessage.class);

        //Use service to join
        try {
            //Retrieve GameList
            GameData gameData = new GameData(-1,null,null,null,null);
            for (GameData game : gameDAO.listGame())
                if (game.gameID() == joinMessage.getGameID())
                     gameData = game;

            if (gameData.gameID() == -1){
                session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Game ID does not exist")));
                return;
            }

            //Retrieve Username
            String username = authDAO.getUsername(joinMessage.getAuthString());


            //Check to see if the color is empty
            if (joinMessage.getColor() == null){
                session.getRemote().sendString(new Gson().toJson(new ErrorMessage("No Color was Given")));
                return;
            }
            //Username Matches White
            else if (Objects.equals(username, gameData.whiteUsername()) && joinMessage.getColor() == ChessGame.TeamColor.WHITE){
                playerConnectionList.addUser(joinMessage.getGameID(), joinMessage.getAuthString());
                session.getRemote().sendString(new Gson().toJson(new LoadGameMessage(gameData.game())));
                String newMessage = username + " joined the game as White";
                playerConnectionList.broadcast(newMessage, joinMessage.getAuthString(), joinMessage.getGameID());
            }
            //Username Matches Black
            else if (Objects.equals(username, gameData.blackUsername()) && joinMessage.getColor() == ChessGame.TeamColor.BLACK){
                playerConnectionList.addUser(joinMessage.getGameID(), joinMessage.getAuthString());
                session.getRemote().sendString(new Gson().toJson(new LoadGameMessage(gameData.game())));
                String newMessage = username + " joined the game as Black";
                playerConnectionList.broadcast(newMessage, joinMessage.getAuthString(), joinMessage.getGameID());
            }
            //If no condition is met throw an error
            else {
                session.getRemote().sendString(new Gson().toJson(new ErrorMessage("")));
                return;
            }
        } catch (DataAccessException e) {
            ErrorMessage errorResponse = new ErrorMessage("Error");
            session.getRemote().sendString(new Gson().toJson(errorResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void joinObserver(Session session, String message) throws IOException, DataAccessException {

        //translate from gson
        JoinObserverMessage joinMessage = new Gson().fromJson(message, JoinObserverMessage.class);

        //Verrify auth token & retrieve username
        if (!authDAO.getAuth(joinMessage.getAuthString())){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Invalid AuthToken")));
            return;
        }
        String username = authDAO.getUsername(joinMessage.getAuthString());

        //Retrieve GameList
        GameData gameData = new GameData(-1,null,null,null,null);
        for (GameData game : gameDAO.listGame())
            if (game.gameID() == joinMessage.getGameID())
                gameData = game;
        //check to make sure ID isn't bad
        if (gameData.gameID() == -1){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Game ID does not exist")));
            return;
        }

        try {

            playerConnectionList.addUser(joinMessage.getGameID(), joinMessage.getAuthString());
            session.getRemote().sendString(new Gson().toJson(new LoadGameMessage(gameData.game())));
            String newMessage = username + " joined the game as an OBSERVER";
            playerConnectionList.broadcast(newMessage, joinMessage.getAuthString(), gameData.gameID());

        } catch (DataAccessException e) {
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Error")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeMove(Session session, String message) throws IOException, DataAccessException {

        //translate Request
        MakeMoveMessage makeMoveRequest = new Gson().fromJson(message, MakeMoveMessage.class);

        //Verrify auth token & retrieve username
        if (!authDAO.getAuth(makeMoveRequest.getAuthString())){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Invalid AuthToken")));
            return;
        }
        String username = authDAO.getUsername(makeMoveRequest.getAuthString());

        //Retrieve GameList
        GameData gameData = new GameData(-1,null,null,null,null);
        for (GameData game : gameDAO.listGame())
            if (game.gameID() == makeMoveRequest.getGameID())
                gameData = game;
        //check to make sure ID isn't bad
        if (gameData.gameID() == -1){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Game ID does not exist")));
            return;
        }

        try {

            if ((Objects.equals(gameData.whiteUsername(), username) && gameData.game().getTeamTurn() == ChessGame.TeamColor.WHITE)){
                gameData.game().makeMove(makeMoveRequest.getMove());
            } else if ((Objects.equals(gameData.blackUsername(), username) && gameData.game().getTeamTurn() == ChessGame.TeamColor.BLACK)){
                gameData.game().makeMove(makeMoveRequest.getMove());
            } else {
                session.getRemote().sendString(new Gson().toJson(new ErrorMessage("It is not your turn")));
                return;
            }

            gameDAO.updateGameData(gameData.gameID(), gameData);
            playerConnectionList.updateGameBoard(gameData.gameID(), gameData.game());
            String msgToSend = username + " made a move.";
            playerConnectionList.broadcast(msgToSend, makeMoveRequest.getAuthString(), gameData.gameID());

        } catch (DataAccessException e) {
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(e.getMessage())));
        } catch (InvalidMoveException e) {
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(e.getMessage())));
        } catch (IOException e) {
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage(e.getMessage())));
        }
    }

    private void leave(Session session, String message){

    }

    private void resign(Session session, String message){

    }
}
