package chess;

import java.util.Collection;
import java.util.Objects;

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
        //check to see if king is in check
            //locate the king of current player
        ChessPosition KingPos;
        for (int j=1; j<9; j++){
            for (int i=1; i<9; i++){
                if (GameBoard.getPiece(i, j) == new ChessPiece(teamTurn, ChessPiece.PieceType.KING)) {
                    KingPos = new ChessPosition(j,i);
                    break;
                }
            }
        }

        //check every piece and see if the king is in check



        //check to see if moving this piece will put your king in check
        ChessBoard CheckGameBoard = GameBoard;          //create a copy of the board


        //look at piece and return valid moves
        return GameBoard.getPiece(startPosition).pieceMoves(GameBoard,startPosition);
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
