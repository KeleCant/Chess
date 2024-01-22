package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    public ChessPosition getStartPosition() {   //return ChessPosition of starting location
        return startPosition;
    }

    public ChessPosition getEndPosition() {     //return ChessPosition of ending location
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        //write promotion code here
        if (promotionPiece == ChessPiece.PieceType.PAWN && endPosition.getRow()==8) {
            return ChessPiece.PieceType.QUEEN;
        }
        else if (promotionPiece == ChessPiece.PieceType.PAWN && endPosition.getRow()==1) {
            return ChessPiece.PieceType.QUEEN;
        }
        else{
            return null;
        }
    }
}
