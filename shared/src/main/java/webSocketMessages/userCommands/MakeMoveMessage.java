package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveMessage extends UserGameCommand {
    private int gameID;
    private ChessMove move;

    public MakeMoveMessage(String authToken, ChessMove move, int gameID) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.move = move;
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }

    //Translate ChessPosition
}
