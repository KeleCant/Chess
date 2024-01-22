package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;
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
        ChessGame.TeamColor PieceColor = board.getPiece(myPosition).getTeamColor();
        Collection<ChessMove> possibleMoves;

        //Pawn
        if (ChessPiece.PieceType.PAWN == PieceType){
            if (PieceColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2){   //if white pawn hasn't moved condition

            }
            else if (PieceColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7){  //if black pawn hasn't moved condition

            }
            else if (PieceColor == ChessGame.TeamColor.WHITE){ //generic pawn move

            }
            else if (PieceColor == ChessGame.TeamColor.BLACK){ //generic pawn move

            }
        }
        //Rook
        else if (ChessPiece.PieceType.ROOK == PieceType){

        }
        //Knight
        else if (ChessPiece.PieceType.KNIGHT == PieceType){

        }
        //Bishop
        else if (ChessPiece.PieceType.BISHOP == PieceType){

        }
        //Queen
        else if (ChessPiece.PieceType.QUEEN == PieceType){

        }
        //King
        else if (ChessPiece.PieceType.KING == PieceType){

        }
        return possibleMoves;
    }
}
