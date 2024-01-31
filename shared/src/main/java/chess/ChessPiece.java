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

            //Up-Right
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() + i, myPosition.getColumn() + i)) {
                    break;
                }
                else if (IsEmpty(i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i), null));
                }
                else if (IsEnemy(i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Down-Right
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() - i, myPosition.getColumn() + i)) {
                    break;
                }
                else if (IsEmpty(-i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i), null));
                }
                else if (IsEnemy(-i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Down-Left
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() - i, myPosition.getColumn() - i)) {
                    break;
                }
                else if (IsEmpty(-i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i), null));
                }
                else if (IsEnemy(-i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Up-Left
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() + i, myPosition.getColumn() - i)) {
                    break;
                }
                else if (IsEmpty(i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i), null));
                }
                else if (IsEnemy(i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i), null));
                    break;
                }
                else{
                    break;
                }
            }
            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK){
            //Up
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() + i, myPosition.getColumn())) {
                    break;
                }
                else if (IsEmpty(i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                }
                else if (IsEnemy(i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Right
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow(), myPosition.getColumn() + i)) {
                    break;
                }
                else if (IsEmpty(0,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                }
                else if (IsEnemy(0,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Down
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() - i, myPosition.getColumn())) {
                    break;
                }
                else if (IsEmpty(-i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                }
                else if (IsEnemy(-i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Left
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow(), myPosition.getColumn() - i)) {
                    break;
                }
                else if (IsEmpty(0,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                }
                else if (IsEnemy(0,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                    break;
                }
                else{
                    break;
                }
            }
            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN){
            //Up-Right
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() + i, myPosition.getColumn() + i)) {
                    break;
                }
                else if (IsEmpty(i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i), null));
                }
                else if (IsEnemy(i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Down-Right
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() - i, myPosition.getColumn() + i)) {
                    break;
                }
                else if (IsEmpty(-i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i), null));
                }
                else if (IsEnemy(-i,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Down-Left
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() - i, myPosition.getColumn() - i)) {
                    break;
                }
                else if (IsEmpty(-i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i), null));
                }
                else if (IsEnemy(-i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Up-Left
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() + i, myPosition.getColumn() - i)) {
                    break;
                }
                else if (IsEmpty(i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i), null));
                }
                else if (IsEnemy(i,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i), null));
                    break;
                }
                else{
                    break;
                }
            }
            //Up
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() + i, myPosition.getColumn())) {
                    break;
                }
                else if (IsEmpty(i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                }
                else if (IsEnemy(i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Right
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow(), myPosition.getColumn() + i)) {
                    break;
                }
                else if (IsEmpty(0,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                }
                else if (IsEnemy(0,i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Down
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow() - i, myPosition.getColumn())) {
                    break;
                }
                else if (IsEmpty(-i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                }
                else if (IsEnemy(-i,0,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                    break;
                }
                else{
                    break;
                }
            }

            //Left
            for (int i=1; i < 8; i++) {
                if (!IsInbounds(myPosition.getRow(), myPosition.getColumn() - i)) {
                    break;
                }
                else if (IsEmpty(0,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                }
                else if (IsEnemy(0,-i,board,myPosition)){
                    collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                    break;
                }
                else{
                    break;
                }
            }
            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KING){
            //up
            if (IsEmpty(1,0,board,myPosition) || IsEnemy(1,0,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), null));
            }
            //up-right
            if (IsEmpty(1,1,board,myPosition) || IsEnemy(1,1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1), null));
            }
            //right
            if (IsEmpty(0,1,board,myPosition) || IsEnemy(0,1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1), null));
            }
            //right-down
            if (IsEmpty(-1,1,board,myPosition) || IsEnemy(-1,1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1), null));
            }
            //down
            if (IsEmpty(-1,0,board,myPosition) || IsEnemy(-1,0,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()), null));
            }
            //down-left
            if (IsEmpty(-1,-1,board,myPosition) || IsEnemy(-1,-1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1), null));
            }
            //left
            if (IsEmpty(0,-1,board,myPosition) || IsEnemy(0,-1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1), null));
            }
            //left-up
            if (IsEmpty(1,-1,board,myPosition) || IsEnemy(1,-1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1), null));
            }
            return collection;
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){
            //up-up-right
            if (IsEmpty(2,1,board,myPosition) || IsEnemy(2,1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1), null));
            }
            //up-right-right
            if (IsEmpty(1,2,board,myPosition) || IsEnemy(1,2,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2), null));
            }
            //down-right-right
            if (IsEmpty(-1,2,board,myPosition) || IsEnemy(-1,2,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2), null));
            }
            //down-down-right
            if (IsEmpty(-2,1,board,myPosition) || IsEnemy(-2,1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1), null));
            }
            //down-down-left
            if (IsEmpty(-2,-1,board,myPosition) || IsEnemy(-2,-1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1), null));
            }
            //down-left-left
            if (IsEmpty(-1,-2,board,myPosition) || IsEnemy(-1,-2,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2), null));
            }
            //up-left-left
            if (IsEmpty(1,-2,board,myPosition) || IsEnemy(1,-2,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2), null));
            }
            //up-up-left
            if (IsEmpty(2,-1,board,myPosition) || IsEnemy(2,-1,board,myPosition)){
                collection.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1), null));
            }
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
