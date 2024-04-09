package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerMessage extends UserGameCommand {
    private int gameID;
    private ChessGame.TeamColor playerColor;
    public JoinPlayerMessage(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getColor() {
        return playerColor;
    }

    public void setColor(ChessGame.TeamColor newColor) {
        playerColor = newColor;
    }
}
