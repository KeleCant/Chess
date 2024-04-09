package webSocketMessages.userCommands;

public class JoinObserverMessage extends UserGameCommand {
    int gameID;
    public JoinObserverMessage(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
