package chess;

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
    public void addPiece(ChessPosition position, ChessPiece piece)
    {
        gameBoard[position.getRow()][position.getColumn()] = piece;
    }
    public void addPiece(int Row, int Col, ChessPiece piece)
    {
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
        return gameBoard[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        addPiece(1,1,WhiteRook1);
        addPiece(1,2,WhiteKnight1);
        addPiece(1,3,WhiteBishop1);
        addPiece(1,4,WhiteQueen);
        addPiece(1,5,WhiteKing);
        addPiece(1,6,WhiteBishop2);
        addPiece(1,7,WhiteKnight2);
        addPiece(1,8,WhiteRook2);
        addPiece(2,1,WhitePawn1);
        addPiece(2,2,WhitePawn2);
        addPiece(2,3,WhitePawn3);
        addPiece(2,4,WhitePawn4);
        addPiece(2,5,WhitePawn5);
        addPiece(2,6,WhitePawn6);
        addPiece(2,7,WhitePawn7);
        addPiece(2,8,WhitePawn8);

        addPiece(8,1,BlackRook1);
        addPiece(8,2,BlackKnight1);
        addPiece(8,3,BlackBishop1);
        addPiece(8,4,BlackQueen);
        addPiece(8,5,BlackKing);
        addPiece(8,6,BlackBishop2);
        addPiece(8,7,BlackKnight2);
        addPiece(8,8,BlackRook2);
        addPiece(7,1,BlackPawn1);
        addPiece(7,2,BlackPawn2);
        addPiece(7,3,BlackPawn3);
        addPiece(7,4,BlackPawn4);
        addPiece(7,5,BlackPawn5);
        addPiece(7,6,BlackPawn6);
        addPiece(7,7,BlackPawn7);
        addPiece(7,8,BlackPawn8);

        //make it whites turn
    }
}
