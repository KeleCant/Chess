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
        if (GameBoard.getPiece(startPosition) == null)
            return null;
            //throw new RuntimeException("No piece at that position");
        //check for checkmate
        if (isInCheckmate(teamTurn))
            return null;
            //throw new RuntimeException("You are in Check, Game over");
        //check for stalemate
        if(isInStalemate(teamTurn))
            return null;
            //throw new RuntimeException("You cannot move, it is a draw");
        //check for stalemate
        //if (isInCheck(teamTurn)){
            //force them to move their king or something in front of the king
            //throw new RuntimeException("not yet implimented");

            Collection<ChessMove> moveList = new HashSet<>();





            //lots of logic required for this bad boy
            //create a new game board, move the king and ask if its in check, if it's not in check add it to the list
            //if its empty throw exeption.







            //Check to see if the king can move
            Collection<ChessPosition> ThisTeamPossibleMoves = new HashSet<>();
            //grabs each potential move this team has
            for (ChessMove M: ReturnTeamPiecesMoves(GameBoard.getPiece(startPosition).getTeamColor())) {

                //creates a new chessboard that we can modify
                ChessBoard CheckGameBoard = new ChessBoard();
                for (int i=1; i<9; i++){
                    for (int j=1; j<9; j++){
                        CheckGameBoard.addPiece(i,j, GameBoard.getPiece(i,j));
                    }
                }


                //make the selected move
                CheckGameBoard.addPiece(M.getEndPosition(), new ChessPiece(GameBoard.getPiece(M.getStartPosition()).getTeamColor(),GameBoard.getPiece(M.getStartPosition()).getPieceType()));
                CheckGameBoard.addPiece(M.getStartPosition(), null);


                //check to see if the King is in check
                if (!isInCheck(CheckGameBoard, teamTurn)){
                    moveList.add(M);
                }
            }


            return moveList;
            //Collection<ChessPosition> Test1 = GetKingMoves(teamColor);
        //}
        //if the piece is a king there are restrictions to movement
        //run normal
        //return GameBoard.getPiece(startPosition).pieceMoves(GameBoard, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //check to make sure it is the right turn
        if (teamTurn != GameBoard.getPiece(move.getStartPosition()).getTeamColor())
            throw new InvalidMoveException("invalid move");

        //look at the start position, look at the end position, look at the promotion piece.
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        var test1 = validMoves;
        var test2 = move.getEndPosition();

        //pawn moves
        if (move.getPromotionPiece() != null){
            //check and make sure it is a pawn
            if (GameBoard.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN){
                //make sure the end position is going into row 1 or 8
                if (move.getEndPosition().getRow()==1 || move.getEndPosition().getRow()==8){
                    //check and make sure moves are valid
                    if (validMoves.contains(move)){
                        //place new piece and removes the old piece
                        GameBoard.addPiece(move.getEndPosition().getRow(), move.getEndPosition().getColumn(), new ChessPiece(teamTurn, move.getPromotionPiece()));
                        GameBoard.addPiece(move.getStartPosition().getRow(), move.getStartPosition().getColumn(), null);
                    } else {
                        //throw new InvalidMoveException();
                        throw new InvalidMoveException("invalid move");
                    }
                }
            } else{
                throw new InvalidMoveException("invalid move");
            }

        } else if (validMoves.contains(move)){
            //place new piece and removes the old piece
            GameBoard.addPiece(move.getEndPosition().getRow(), move.getEndPosition().getColumn(), GameBoard.getPiece(move.getStartPosition()));
            GameBoard.addPiece(move.getStartPosition().getRow(), move.getStartPosition().getColumn(), null);
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
        Collection<ChessPosition> EnemyMoves = ReturnEnemyMoves(GameBoard, teamColor);
        if (EnemyMoves.contains(FindKingPosition(teamColor))){
            return true;
        } else {
            return false;
        }
    }

    public boolean isInCheck(ChessBoard GameBoard,TeamColor teamColor) {
        Collection<ChessPosition> EnemyMoves = ReturnEnemyMoves(GameBoard, teamColor);
        if (EnemyMoves.contains(FindKingPosition(GameBoard, teamColor))){
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

        //logic code for tests
        if (FindKingPosition(teamColor) == null)
            return false;

        if (isInCheck(teamColor))
            return false;

        //check to see if any pieces can move that are not the king
        if (ReturnTeamMoves(teamColor).size() > GetKingMoves(teamColor).size()){
            return false;
        }

        //Check to see if the king can move
        for (ChessPosition M: GetKingMoves(teamColor)){
            if (!ReturnEnemyMoves(GameBoard, teamColor).contains(M)){
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
        for (ChessPosition M: GetKingMoves(teamColor)) {
            //creates a new chessboard step by step
            ChessBoard CheckGameBoard = new ChessBoard();
            for (int i=1; i<9; i++){
                for (int j=1; j<9; j++){
                    CheckGameBoard.addPiece(i,j, GameBoard.getPiece(i,j));
                }
            }

            //creates a new board with the king moved to this position
            CheckGameBoard.addPiece(M.getRow(), M.getColumn(), new ChessPiece(teamColor, ChessPiece.PieceType.KING));
            CheckGameBoard.addPiece(FindKingPosition(teamColor), null);

            totalEnemyMoves.addAll(ReturnEnemyMoves(CheckGameBoard,teamColor));
        }

        //Collection<ChessPosition> Test1 = GetKingMoves(teamColor);
        for (ChessPosition M: GetKingMoves(teamColor)) {
            if (!totalEnemyMoves.contains(M)){
                return false;
            }
        }
        //fixme This code only looks at the kings position. If I was to fully Impliment a solution I would need the system to check every piece's moves to see if they can block the check.
        return true;
    }

    public ChessPosition FindKingPosition(TeamColor teamColor){
        for (int j=1; j<9; j++){
            for (int i=1; i<9; i++) {
                if (GameBoard.getPiece(i, j) != null) {
                    if (GameBoard.getPiece(i, j).getPieceType() == ChessPiece.PieceType.KING && GameBoard.getPiece(i, j).getTeamColor() == teamColor) {
                        return new ChessPosition(i, j);
                    }
                }
            }
        }
        //throw InvalidMoveException("No King Found");
        //throw new RuntimeException("No King Found");
        return null;
    }

    public ChessPosition FindKingPosition(ChessBoard GameBoard, TeamColor teamColor){
        for (int j=1; j<9; j++){
            for (int i=1; i<9; i++) {
                if (GameBoard.getPiece(i, j) != null) {
                    if (GameBoard.getPiece(i, j).getPieceType() == ChessPiece.PieceType.KING && GameBoard.getPiece(i, j).getTeamColor() == teamColor) {
                        return new ChessPosition(i, j);
                    }
                }
            }
        }
        //throw InvalidMoveException("No King Found");
        //throw new RuntimeException("No King Found");
        return null;
    }

    public Collection<ChessPosition> GetKingMoves(TeamColor teamColor){
        Collection<ChessPosition> moves = new HashSet<>();
        for (ChessMove M : GameBoard.getPiece(FindKingPosition(teamColor)).pieceMoves(GameBoard, FindKingPosition(teamColor))) {
            moves.add(M.getEndPosition());
        }
        return moves;
    }

    public Collection<ChessPosition> ReturnEnemyMoves(ChessBoard GameBoard, TeamColor teamColor){     //fixme pawns potential moves need to be added to this list
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
//        //locate every pawn
//        //add their offensive positions to this
//        for (int i=1; i<9; i++){
//            for (int j=1; j<9; j++){
//                if (GameBoard.getPiece(i, j) != null) {
//                    if (GameBoard.getPiece(i, j).getTeamColor() != teamColor || GameBoard.getPiece(i, j).getPieceType() == ChessPiece.PieceType.PAWN) {
//                        if (GameBoard.getPiece(i, j).getTeamColor() == TeamColor.WHITE){
//                            //add white pawn offensive moves
//                            moves.add()
//                        } else {
//                            //add black pawn offensive moves
//                            moves.add()
//                        }
//                    }
        return moves;
    }

    public Collection<ChessPosition> ReturnTeamMoves(TeamColor teamColor){
        Collection<ChessPosition> moves = new HashSet<>();
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                if (GameBoard.getPiece(i, j) != null) {
                    if (GameBoard.getPiece(i, j).getTeamColor() == teamColor) {
                        for (ChessMove M : GameBoard.getPiece(i, j).pieceMoves(GameBoard, new ChessPosition(i, j))) {
                            moves.add(M.getEndPosition());
                        }
                    }
                }
            }
        }
        return moves;
    }


    public Collection<ChessMove>  ReturnTeamPiecesMoves(TeamColor teamColor){
        Collection<ChessMove> moves = new HashSet<>();
        for (int i=1; i<9; i++){
            for (int j=1; j<9; j++){
                if (GameBoard.getPiece(i, j) != null) {
                    if (GameBoard.getPiece(i, j).getTeamColor() == teamColor) {
                        moves.addAll(GameBoard.getPiece(i,j).pieceMoves(GameBoard, new ChessPosition(i,j)));
                    }
                }
            }
        }
        return moves;
    }





    public void setBoard(ChessBoard board) {       //sets board
        GameBoard = board;
    }

    public ChessBoard getBoard() {      //returns GameBoard
        return GameBoard;
    }
}
