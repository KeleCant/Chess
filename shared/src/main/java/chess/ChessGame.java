package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard gameBoard = new ChessBoard();
    private TeamColor teamTurn;
    private Boolean isGameOver = false;

    public ChessGame() {
        gameBoard.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    public void endGame(){
        isGameOver = true;
    }

    public boolean isGameOver(){
        return isGameOver;
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
            teamTurn = TeamColor.WHITE;
        } else{
            teamTurn = TeamColor.BLACK;
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
        Collection<ChessMove> moveList = new HashSet<>();

        if (gameBoard.getPiece(startPosition) == null)
            return null;
        //check for checkmate
        if (isInCheckmate(gameBoard.getPiece(startPosition).getTeamColor()))
            return moveList;
        //check for stalemate
        if (isInStalemate(gameBoard.getPiece(startPosition).getTeamColor()))
            return moveList;
        if (isInCheck(gameBoard.getPiece(startPosition).getTeamColor())){
            //check to see if move takes you out of
            for (ChessMove m: gameBoard.getPiece(startPosition).pieceMoves(gameBoard, startPosition)) {
                //creates a new chessboard that we can modify
                ChessBoard testGameBoard = new ChessBoard();
                for (int i=1; i<9; i++){
                    for (int j=1; j<9; j++){
                        testGameBoard.addPiece(i,j, gameBoard.getPiece(i,j));
                    }
                }

                //make the selected move
                testGameBoard.addPiece(m.getEndPosition(), new ChessPiece(gameBoard.getPiece(startPosition).getTeamColor(),testGameBoard.getPiece(m.getStartPosition()).getPieceType()));
                testGameBoard.addPiece(m.getStartPosition(), null);

                //check to see if the King is in check
                if (isNotCheck(testGameBoard, gameBoard.getPiece(startPosition).getTeamColor())){
                    moveList.add(m);
                }
            }
        }

        //Collection<ChessMove> Test1 = ReturnTeamPiecesMoves(GameBoard.getPiece(startPosition).getTeamColor());
        for (ChessMove m: gameBoard.getPiece(startPosition).pieceMoves(gameBoard, startPosition)) {
            //creates a new chessboard that we can modify
            ChessBoard testGameBoard = new ChessBoard();
            for (int i=1; i<9; i++){
                for (int j=1; j<9; j++){
                    testGameBoard.addPiece(i,j, gameBoard.getPiece(i,j));
                }
            }

            //make the selected move
            testGameBoard.addPiece(m.getEndPosition(), new ChessPiece(testGameBoard.getPiece(m.getStartPosition()).getTeamColor(),testGameBoard.getPiece(m.getStartPosition()).getPieceType()));
            testGameBoard.addPiece(m.getStartPosition(), null);

            //check to see if the King is in check
            if (isNotCheck(testGameBoard, gameBoard.getPiece(startPosition).getTeamColor())){
                moveList.add(m);
            }
        }
        return moveList;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //check to make sure it is the right turn
        if (teamTurn != gameBoard.getPiece(move.getStartPosition()).getTeamColor())
            throw new InvalidMoveException("invalid move");

        //look at the start position, look at the end position, look at the promotion piece.
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        //pawn moves
        if (move.getPromotionPiece() != null){
            //check and make sure it is a pawn
            if (gameBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN){
                //make sure the end position is going into row 1 or 8
                if (move.getEndPosition().getRow()==1 || move.getEndPosition().getRow()==8){
                    //check and make sure moves are valid
                    if (validMoves.contains(move)){
                        //place new piece and removes the old piece
                        gameBoard.addPiece(move.getEndPosition().getRow(), move.getEndPosition().getColumn(), new ChessPiece(teamTurn, move.getPromotionPiece()));
                        gameBoard.addPiece(move.getStartPosition().getRow(), move.getStartPosition().getColumn(), null);
                    } else {
                        throw new InvalidMoveException("invalid move");
                    }
                }
            } else{
                throw new InvalidMoveException("invalid move");
            }

        } else if (validMoves.contains(move)){
            //place new piece and removes the old piece
            gameBoard.addPiece(move.getEndPosition().getRow(), move.getEndPosition().getColumn(), gameBoard.getPiece(move.getStartPosition()));
            gameBoard.addPiece(move.getStartPosition().getRow(), move.getStartPosition().getColumn(), null);
        } else {
            //throw new InvalidMoveException();
            throw new InvalidMoveException("invalid move");
        }


        if (teamTurn == TeamColor.WHITE){
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessPosition> enemyMoves = returnEnemyMoves(gameBoard, teamColor);
        return enemyMoves.contains(findKingPosition(gameBoard, teamColor));
    }
    public boolean isNotCheck(ChessBoard gameBoard,TeamColor teamColor) {
        Collection<ChessPosition> enemyMoves = returnEnemyMoves(gameBoard, teamColor);
        if (findKingPosition(gameBoard, teamColor) == null)
            return true;
        return !enemyMoves.contains(findKingPosition(gameBoard, teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        //logic code for tests
        if (findKingPosition(gameBoard, teamColor) == null)
            return false;

        if (isInCheck(teamColor))
            return false;

        //getenemyteam moves
        TeamColor enemyteamColor;
        if(teamColor == TeamColor.WHITE)
            enemyteamColor = TeamColor.BLACK;
        else
            enemyteamColor = TeamColor.WHITE;

        //check to see if any pieces can move that are not the king
        if (returnEnemyMoves(gameBoard, enemyteamColor).size() > getKingMoves(teamColor).size()){
            return false;
        }

        //Check to see if the king can move
        for (ChessPosition m: getKingMoves(teamColor)){
            if (!returnEnemyMoves(gameBoard, teamColor).contains(m)){
                return false;
            }
        }

        //if no conditions are met return true
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //check to see if in check
        if (!isInCheck(teamColor))
            return false;

        //Check to see if the king can move
        Collection<ChessPosition> totalEnemyMoves = new HashSet<>();
        for (ChessPosition m: getKingMoves(teamColor)) {
            //creates a new chessboard step by step
            ChessBoard checkGameBoard = new ChessBoard();
            for (int i=1; i<9; i++){
                for (int j=1; j<9; j++){
                    checkGameBoard.addPiece(i,j, gameBoard.getPiece(i,j));
                }
            }

            //creates a new board with the king moved to this position
            checkGameBoard.addPiece(m.getRow(), m.getColumn(), new ChessPiece(teamColor, ChessPiece.PieceType.KING));
            checkGameBoard.addPiece(findKingPosition(gameBoard, teamColor), null);

            totalEnemyMoves.addAll(returnEnemyMoves(checkGameBoard,teamColor));
        }

        //Collection<ChessPosition> Test1 = GetKingMoves(teamColor);
        for (ChessPosition m: getKingMoves(teamColor)) {
            if (!totalEnemyMoves.contains(m)){
                return false;
            }
        }
        //fixme This code only looks at the kings position. If I was to fully Impliment a solution I would need the system to check every piece's moves to see if they can block the check.
        return true;
    }

    public ChessPosition findKingPosition(ChessBoard gameBoard, TeamColor teamColor){
        for (int j=1; j<9; j++){
            for (int i=1; i<9; i++) {
                if (gameBoard.getPiece(i, j) != null) {
                    if (gameBoard.getPiece(i, j).getPieceType() == ChessPiece.PieceType.KING && gameBoard.getPiece(i, j).getTeamColor() == teamColor) {
                        return new ChessPosition(i, j);
                    }
                }
            }
        }
        return null;
    }

    public Collection<ChessPosition> getKingMoves(TeamColor teamColor){
        Collection<ChessPosition> moves = new HashSet<>();
        for (ChessMove m : gameBoard.getPiece(findKingPosition(gameBoard, teamColor)).pieceMoves(gameBoard, findKingPosition(gameBoard, teamColor))) {
            moves.add(m.getEndPosition());
        }
        return moves;
    }

    public Collection<ChessPosition> returnEnemyMoves(ChessBoard gameBoard, TeamColor teamColor){
        Collection<ChessPosition> moves = new HashSet<>();
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                if (gameBoard.getPiece(i, j) != null) {
                    if (gameBoard.getPiece(i, j).getTeamColor() != teamColor) {
                        for (ChessMove m : gameBoard.getPiece(i, j).pieceMoves(gameBoard, new ChessPosition(i, j))) {
                            moves.add(m.getEndPosition());
                        }
                    }
                }
            }
        }
        return moves;
    }

    public void setBoard(ChessBoard board) {       //sets board
        gameBoard = board;
    }

    public ChessBoard getBoard() {      //returns GameBoard
        return gameBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(gameBoard, chessGame.gameBoard) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameBoard, teamTurn);
    }
}
