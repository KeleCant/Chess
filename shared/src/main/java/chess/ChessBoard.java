package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece BlackRook1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    private ChessPiece BlackRook2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    private ChessPiece BlackBishop1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    private ChessPiece BlackBishop2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    private ChessPiece BlackKnight1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    private ChessPiece BlackKnight2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    private ChessPiece BlackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
    private ChessPiece BlackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    private ChessPiece BlackPawn1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn3 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn4 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn5 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn6 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private ChessPiece BlackPawn8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);

    private ChessPiece WhiteRook1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    private ChessPiece WhiteRook2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    private ChessPiece WhiteBishop1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    private ChessPiece WhiteBishop2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    private ChessPiece WhiteKnight1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    private ChessPiece WhiteKnight2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    private ChessPiece WhiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
    private ChessPiece WhiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
    private ChessPiece WhitePawn1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn3 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn4 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn5 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn6 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn7 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private ChessPiece WhitePawn8 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

    private ChessPiece[][] gameBoard = new ChessPiece[8][8]; //this creates a 8 by 8 grid with each pos on the grid representing a ChessPiece class
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        gameBoard[position.getRow()-1][position.getColumn()-1] = piece;
    }
    public void addPiece(int Row, int Col, ChessPiece piece) {
        gameBoard[Row][Col] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return gameBoard[position.getRow()-1][position.getColumn()-1];
    }
    public ChessPiece getPiece(int row, int col) {
        return gameBoard[row-1][col-1];
    }

    public boolean isOccupied(int row, int col) {
        return gameBoard[row-1][col-1] != null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        addPiece(0,0,WhiteRook1);
        addPiece(0,1,WhiteKnight1);
        addPiece(0,2,WhiteBishop1);
        addPiece(0,3,WhiteQueen);
        addPiece(0,4,WhiteKing);
        addPiece(0,5,WhiteBishop2);
        addPiece(0,6,WhiteKnight2);
        addPiece(0,7,WhiteRook2);
        addPiece(1,0,WhitePawn1);
        addPiece(1,1,WhitePawn2);
        addPiece(1,2,WhitePawn3);
        addPiece(1,3,WhitePawn4);
        addPiece(1,4,WhitePawn5);
        addPiece(1,5,WhitePawn6);
        addPiece(1,6,WhitePawn7);
        addPiece(1,7,WhitePawn8);

        addPiece(7,0,BlackRook1);
        addPiece(7,1,BlackKnight1);
        addPiece(7,2,BlackBishop1);
        addPiece(7,3,BlackQueen);
        addPiece(7,4,BlackKing);
        addPiece(7,5,BlackBishop2);
        addPiece(7,6,BlackKnight2);
        addPiece(7,7,BlackRook2);
        addPiece(6,0,BlackPawn1);
        addPiece(6,1,BlackPawn2);
        addPiece(6,2,BlackPawn3);
        addPiece(6,3,BlackPawn4);
        addPiece(6,4,BlackPawn5);
        addPiece(6,5,BlackPawn6);
        addPiece(6,6,BlackPawn7);
        addPiece(6,7,BlackPawn8);

        //have this remove added pieces, set everything in scope to null
        for (var j = 2; j < 6; j++) { //for rows
            for (var i = 0; i < 8; i++) {   //for columns
                addPiece(i, j, null);
            }
        }

        //make it whites turn

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.equals(BlackRook1, that.BlackRook1) && Objects.equals(BlackRook2, that.BlackRook2) && Objects.equals(BlackBishop1, that.BlackBishop1) && Objects.equals(BlackBishop2, that.BlackBishop2) && Objects.equals(BlackKnight1, that.BlackKnight1) && Objects.equals(BlackKnight2, that.BlackKnight2) && Objects.equals(BlackQueen, that.BlackQueen) && Objects.equals(BlackKing, that.BlackKing) && Objects.equals(BlackPawn1, that.BlackPawn1) && Objects.equals(BlackPawn2, that.BlackPawn2) && Objects.equals(BlackPawn3, that.BlackPawn3) && Objects.equals(BlackPawn4, that.BlackPawn4) && Objects.equals(BlackPawn5, that.BlackPawn5) && Objects.equals(BlackPawn6, that.BlackPawn6) && Objects.equals(BlackPawn7, that.BlackPawn7) && Objects.equals(BlackPawn8, that.BlackPawn8) && Objects.equals(WhiteRook1, that.WhiteRook1) && Objects.equals(WhiteRook2, that.WhiteRook2) && Objects.equals(WhiteBishop1, that.WhiteBishop1) && Objects.equals(WhiteBishop2, that.WhiteBishop2) && Objects.equals(WhiteKnight1, that.WhiteKnight1) && Objects.equals(WhiteKnight2, that.WhiteKnight2) && Objects.equals(WhiteQueen, that.WhiteQueen) && Objects.equals(WhiteKing, that.WhiteKing) && Objects.equals(WhitePawn1, that.WhitePawn1) && Objects.equals(WhitePawn2, that.WhitePawn2) && Objects.equals(WhitePawn3, that.WhitePawn3) && Objects.equals(WhitePawn4, that.WhitePawn4) && Objects.equals(WhitePawn5, that.WhitePawn5) && Objects.equals(WhitePawn6, that.WhitePawn6) && Objects.equals(WhitePawn7, that.WhitePawn7) && Objects.equals(WhitePawn8, that.WhitePawn8) && Arrays.equals(gameBoard, that.gameBoard);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(BlackRook1, BlackRook2, BlackBishop1, BlackBishop2, BlackKnight1, BlackKnight2, BlackQueen, BlackKing, BlackPawn1, BlackPawn2, BlackPawn3, BlackPawn4, BlackPawn5, BlackPawn6, BlackPawn7, BlackPawn8, WhiteRook1, WhiteRook2, WhiteBishop1, WhiteBishop2, WhiteKnight1, WhiteKnight2, WhiteQueen, WhiteKing, WhitePawn1, WhitePawn2, WhitePawn3, WhitePawn4, WhitePawn5, WhitePawn6, WhitePawn7, WhitePawn8);
        result = 31 * result + Arrays.deepHashCode(gameBoard);
        return result;
    }
}
