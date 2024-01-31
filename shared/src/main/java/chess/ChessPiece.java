package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
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
        HashSet<ChessMove> collection = new HashSet<>();

        //Bishop
        if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP){
            collection = AddMultiplePosition(1, 1, board, myPosition, collection);      //Up-Right
            collection = AddMultiplePosition(-1, 1, board, myPosition, collection);     //Down-Right
            collection = AddMultiplePosition(-1, -1, board, myPosition, collection);    //Down-Left
            collection = AddMultiplePosition(1, -1, board, myPosition, collection);     //Up-Left

            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK){
            collection = AddMultiplePosition(1, 0, board, myPosition, collection);      //Up
            collection = AddMultiplePosition(0, 1, board, myPosition, collection);      //Right
            collection = AddMultiplePosition(-1, 0, board, myPosition, collection);     //Down
            collection = AddMultiplePosition(0, -1, board, myPosition, collection);     //Left

            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN){
            collection = AddMultiplePosition(1, 1, board, myPosition, collection);      //Up-Right
            collection = AddMultiplePosition(-1, 1, board, myPosition, collection);     //Down-Right
            collection = AddMultiplePosition(-1, -1, board, myPosition, collection);    //Down-Left
            collection = AddMultiplePosition(1, -1, board, myPosition, collection);     //Up-Left
            collection = AddMultiplePosition(1, 0, board, myPosition, collection);      //Up
            collection = AddMultiplePosition(0, 1, board, myPosition, collection);      //Right
            collection = AddMultiplePosition(-1, 0, board, myPosition, collection);     //Down
            collection = AddMultiplePosition(0, -1, board, myPosition, collection);     //Left

            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KING){
            collection = AddSinglePosition(1,0, board, myPosition, collection);     //up
            collection = AddSinglePosition(1,1, board, myPosition, collection);     //up-right
            collection = AddSinglePosition(0,1, board, myPosition, collection);     //right
            collection = AddSinglePosition(-1,1, board, myPosition, collection);    //down-right
            collection = AddSinglePosition(-1,0, board, myPosition, collection);    //down
            collection = AddSinglePosition(-1,-1, board, myPosition, collection);   //down-left
            collection = AddSinglePosition(0,-1, board, myPosition, collection);    //left
            collection = AddSinglePosition(1,-1, board, myPosition, collection);    //up-left
            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){
            collection = AddSinglePosition(2,1, board, myPosition, collection);      //up-up-right
            collection = AddSinglePosition(1,2, board, myPosition, collection);      //up-right-right
            collection = AddSinglePosition(-1,2, board, myPosition, collection);     //down-right-right
            collection = AddSinglePosition(-2,1, board, myPosition, collection);     //down-down-right
            collection = AddSinglePosition(-2,-1, board, myPosition, collection);    //down-down-left
            collection = AddSinglePosition(-1,-2, board, myPosition, collection);    //down-left-left
            collection = AddSinglePosition(1,-2, board, myPosition, collection);     //up-left-left
            collection = AddSinglePosition(2,-1, board, myPosition, collection);      //up-up-left
            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN){
            //White
            if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
                if (IsEmpty(1,0,board,myPosition) && IsEmpty(2,0,board,myPosition) && myPosition.getRow()==2){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()), null));
                }
                if (IsEmpty(1,0,board,myPosition)){
                    if(myPosition.getRow()+1==8){
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), PieceType.QUEEN));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), PieceType.ROOK));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), PieceType.BISHOP));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), PieceType.KNIGHT));
                    }
                    else {
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), null));
                    }
                }
                if (IsEnemy(1,1,board,myPosition)){
                    if(myPosition.getRow()+1==8){
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1), PieceType.QUEEN));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1), PieceType.ROOK));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1), PieceType.BISHOP));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1), PieceType.KNIGHT));
                    }
                    else {
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1), null));
                    }
                }
                if (IsEnemy(1,-1,board,myPosition)){
                    if(myPosition.getRow()+1==8){
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1), PieceType.QUEEN));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1), PieceType.ROOK));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1), PieceType.BISHOP));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1), PieceType.KNIGHT));
                    }
                    else {
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1), null));
                    }
                }
            }
            //Black
            else{
                if (IsEmpty(-1,0,board,myPosition) && IsEmpty(-2,0,board,myPosition) && myPosition.getRow()==7){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()), null));
                }
                if (IsEmpty(-1,0,board,myPosition)){
                    if(myPosition.getRow()-1==1){
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()), PieceType.QUEEN));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()), PieceType.ROOK));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()), PieceType.BISHOP));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()), PieceType.KNIGHT));
                    }
                    else {
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()), null));
                    }
                }
                if (IsEnemy(-1,1,board,myPosition)){
                    if(myPosition.getRow()-1==1){
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1), PieceType.QUEEN));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1), PieceType.ROOK));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1), PieceType.BISHOP));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1), PieceType.KNIGHT));
                    }
                    else {
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1), null));
                    }
                }
                if (IsEnemy(-1,-1,board,myPosition)){
                    if(myPosition.getRow()-1==1){
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1), PieceType.QUEEN));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1), PieceType.ROOK));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1), PieceType.BISHOP));
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1), PieceType.KNIGHT));
                    }
                    else {
                        collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1), null));
                    }
                }

            }
            return collection;
        }
        return collection;
    }

    public boolean IsEmpty(int up, int right, ChessBoard board, ChessPosition myPosition){
        if (IsInbounds(myPosition.getRow()+up, myPosition.getColumn()+right)){
            if (board.getPiece(myPosition.getRow()+up, myPosition.getColumn()+right) == null){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean IsEnemy(int up, int right, ChessBoard board, ChessPosition myPosition){
        if(IsInbounds(myPosition.getRow()+up, myPosition.getColumn()+right)){
            if (board.getPiece(myPosition.getRow()+up, myPosition.getColumn()+right) != null){
                if (board.getPiece(myPosition.getRow()+up, myPosition.getColumn()+right).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return false;
    }

    private HashSet<ChessMove> AddSinglePosition(int up, int right, ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> collection){
        if (IsEmpty(up,right,board,myPosition) || IsEnemy(up,right,board,myPosition))
            collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+up, myPosition.getColumn()+right), null));
        return collection;
    }

    private HashSet<ChessMove> AddMultiplePosition(int up, int right, ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> collection) {
        for (int i = 1; i < 8; i++) {
            if (!IsInbounds(myPosition.getRow() + i*up, myPosition.getColumn() + i*right)) {
                break;
            } else if (IsEmpty(i*up, i*right, board, myPosition)) {
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i*up, myPosition.getColumn() + i*right), null));
            } else if (IsEnemy(i*up, i*right, board, myPosition)) {
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i*up, myPosition.getColumn() + i*right), null));
                break;
            } else {
                break;
            }
        }
        return collection;
    }


    public boolean IsInbounds(int row, int col){
        if (row > 8 || row < 1 || col > 8 || col < 1){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
