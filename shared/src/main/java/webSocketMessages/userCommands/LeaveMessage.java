package webSocketMessages.userCommands;

public class LeaveMessage extends UserGameCommand {
    int gameID;
    public LeaveMessage(String authToken, int gameID) {
        super(authToken);
        commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
