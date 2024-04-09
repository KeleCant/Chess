package webSocketMessages.userCommands;

public class ResignMessage extends UserGameCommand {
    int gameID;
    public ResignMessage(String authToken, int gameID) {
        super(authToken);
        commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
