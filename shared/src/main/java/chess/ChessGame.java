package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard GameBoard = new ChessBoard();
    private TeamColor teamTurn;

    public ChessGame() {
        GameBoard.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    public TeamColor getTeamTurn() { //return Which team's turn it is
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team == TeamColor.WHITE){
            teamTurn = TeamColor.BLACK;
        } else{
            teamTurn = TeamColor.WHITE;
        }
    }

    public enum TeamColor { //Enum identifying the 2 possible teams in a chess game
        WHITE, BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        //check for checkmate
        if (!isInCheckmate(teamTurn)){
            throw new RuntimeException("You are in Check, Game over");
        }
        //check for stalemate
        else if(!isInStalemate(teamTurn)){
            throw new RuntimeException("You cannot move, it is a draw");
        }
        //check for stalemate
        else if (!isInCheck(teamTurn)){
            //force them to move their king or something in front of the king
        }
        //run normal
        else{
            //look at piece and return valid moves
            //make sure this move doesn't put you in check
            return GameBoard.getPiece(startPosition).pieceMoves(GameBoard,startPosition);
        }
        throw new RuntimeException("Error line 68");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessPosition> EnemyMoves = ReturnEnemyMoves(teamColor);
        if (EnemyMoves.contains(FindKingPosition())){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor) && isInStalemate(teamColor)){
            return true;
        } else {
            return false;
        }
    }

    public ChessPosition FindKingPosition(){
        for (int j=1; j<9; j++){
            for (int i=1; i<9; i++) {
                if (GameBoard.getPiece(i, j) != null) {
                    if (GameBoard.getPiece(i, j).getPieceType() == ChessPiece.PieceType.KING) {
                        return new ChessPosition(i, j);
                    }
                }
            }
        }
        throw new RuntimeException("No King Found");
    }

    public Collection<ChessPosition> ReturnEnemyMoves(TeamColor teamColor){
        Collection<ChessPosition> moves = new HashSet<>();
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                if (GameBoard.getPiece(i, j) != null) {
                    if (GameBoard.getPiece(i, j).getTeamColor() != teamColor) {
                        for (ChessMove M : GameBoard.getPiece(i, j).pieceMoves(GameBoard, new ChessPosition(i, j))) {
                            moves.add(M.getEndPosition());
                        }
                    }
                }
            }
        }
        return moves;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        GameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return GameBoard;
    }
}
