package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;
    private int timesMoved = 0;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    public enum PieceType { //The various different chess piece options
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
    }

    public ChessGame.TeamColor getTeamColor() { //return Which team this chess piece belongs to
        return pieceColor;
    }

    public PieceType getPieceType() { //return which type of chess piece this piece is
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece.PieceType PieceType = board.getPiece(myPosition).getPieceType(); //reads the bored records our piece type
        //array[][] possibleMoves

        //implimnent moves here
        //the input is a board and a pos... the pos tells us the peice on the bored being moved
        //get peice id from bored
        //run through if statements, one for each enum
        //something will have to read the avalible pos and return if the pos is ocucpied
        //stop

        //use if then statements to add to an array




        return null;
    }
}
