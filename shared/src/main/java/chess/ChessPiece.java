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
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
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
        //valuable that will return a collection of ChessMoves
        //Collection<ChessMove> returnValue = new ArrayList<>();
        var returnValue = new HashSet<ChessMove>();
        //variable to determine what piece type this is
        PieceType PieceType = board.getPiece(myPosition).getPieceType();
        ChessGame.TeamColor PieceColor = board.getPiece(myPosition).getTeamColor();
        //variable that will be used to modify and add to the collection
        ChessPosition newPosition;

        //Bishop
        if (PieceType == ChessPiece.PieceType.BISHOP) {
            //up-right
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() + i > 8) {
                    break;  //if boundaries are left, terminate
                }
                if (myPosition.getColumn() + i > 8) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() + i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() + i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //down-right
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() - i < 1) {
                    break;
                }
                if (myPosition.getColumn() + i > 8) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() + i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() + i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //down-left
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() - i < 1) {
                    break;
                }
                if (myPosition.getColumn() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() - i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() - i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //up-left
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() + i > 8) {
                    break;
                }
                if (myPosition.getColumn() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() - i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() - i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }
            return returnValue;
        }
        //King
        else if (PieceType == ChessPiece.PieceType.KING) {
            //Up
            //check to see if there are pieces in the way
            if (CheckInBounds(myPosition.getRow() + 1, myPosition.getColumn())) {
                if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn()) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn()).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                    }
                }
            }

            //Up-Right
            if (CheckInBounds(myPosition.getRow() + 1, myPosition.getColumn() + 1)) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn() + 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn() + 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                    }
                }
            }

            //Right
            if(CheckInBounds(myPosition.getRow(), myPosition.getColumn()+1)) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow(), myPosition.getColumn() + 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow(), myPosition.getColumn() + 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), null));
                    }
                }
            }

            //Right-Down
            if(CheckInBounds(myPosition.getRow() -1, myPosition.getColumn()+1)) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - 1, myPosition.getColumn() + 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - 1, myPosition.getColumn() + 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                    }
                }
            }

            //Down
            if(CheckInBounds(myPosition.getRow() -1, myPosition.getColumn())) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - 1, myPosition.getColumn()) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - 1, myPosition.getColumn()).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                    }
                }
            }

            //Down-Left
            if (CheckInBounds(myPosition.getRow() -1, myPosition.getColumn()-1)) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - 1, myPosition.getColumn() - 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - 1, myPosition.getColumn() - 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                    }
                }
            }

            //Left
            if(CheckInBounds(myPosition.getRow(), myPosition.getColumn()-1)) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow(), myPosition.getColumn() - 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow(), myPosition.getColumn() - 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), null));
                    }
                }
            }

            //Up-Left
            if (CheckInBounds(myPosition.getRow()+1, myPosition.getColumn()-1)) {
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn() - 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn() - 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                    }
                }
            }
            return returnValue;
        }
        //Knight
        else if (PieceType == ChessPiece.PieceType.KNIGHT) {
            //up-up-right
            if(CheckInBounds(myPosition.getRow() + 2, myPosition.getColumn() + 1)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + 2, myPosition.getColumn() + 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + 2, myPosition.getColumn() + 1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
                    }
                }
            }
            //up-right-right
            if(CheckInBounds(myPosition.getRow() + 1, myPosition.getColumn() + 2)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn() + 2) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + 1, myPosition.getColumn() + 2).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2), null));
                    }
                }
            }
            //down-right-right
            if(CheckInBounds(myPosition.getRow() -1, myPosition.getColumn() + 2)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() -1, myPosition.getColumn() + 2) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -1, myPosition.getColumn() + 2), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() -1, myPosition.getColumn() +2).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -1, myPosition.getColumn() + 2), null));
                    }
                }
            }
            //down-down-right
            if(CheckInBounds(myPosition.getRow() -2, myPosition.getColumn() + 1)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() -2, myPosition.getColumn() + 1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -2, myPosition.getColumn() + 1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() -2, myPosition.getColumn() +1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -2, myPosition.getColumn() + 1), null));
                    }
                }
            }
            //down-down-left
            if(CheckInBounds(myPosition.getRow() -2, myPosition.getColumn() -1)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() -2, myPosition.getColumn() -1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -2, myPosition.getColumn() -1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() -2, myPosition.getColumn() -1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -2, myPosition.getColumn() -1), null));
                    }
                }
            }
            //down-left-left
            if(CheckInBounds(myPosition.getRow() -1, myPosition.getColumn() -2)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() -1, myPosition.getColumn() -2) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -1, myPosition.getColumn() -2), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() -1, myPosition.getColumn() -2).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() -1, myPosition.getColumn() -2), null));
                    }
                }
            }
            //up-left-left
            if(CheckInBounds(myPosition.getRow() +1, myPosition.getColumn() -2)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() +1, myPosition.getColumn() -2) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() +1, myPosition.getColumn() -2), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() +1, myPosition.getColumn() -2).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() +1, myPosition.getColumn() -2), null));
                    }
                }
            }
            //up-up-left
            if(CheckInBounds(myPosition.getRow() +2, myPosition.getColumn() -1)){
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() +2, myPosition.getColumn() -1) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() +2, myPosition.getColumn() -1), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() +2, myPosition.getColumn() -1).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() +2, myPosition.getColumn() -1), null));
                    }
                }
            }
            return returnValue;
        }
        //pawn
        else if (PieceType == ChessPiece.PieceType.PAWN) {

            return returnValue;
        }
        //queen
        else if (PieceType == ChessPiece.PieceType.QUEEN) {
            //up-right
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() + i > 8) {
                    break;  //if boundaries are left, terminate
                }
                if (myPosition.getColumn() + i > 8) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() + i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() + i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //down-right
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() - i < 1) {
                    break;
                }
                if (myPosition.getColumn() + i > 8) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() + i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() + i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //down-left
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() - i < 1) {
                    break;
                }
                if (myPosition.getColumn() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() - i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn() - i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //up-left
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() + i > 8) {
                    break;
                }
                if (myPosition.getColumn() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() - i) == null) {
                    newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                    returnValue.add(new ChessMove(myPosition, newPosition, null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn() - i).getTeamColor() != PieceColor) {
                        newPosition = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                        returnValue.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() + i > 8) {
                    break;  //if boundaries are left, terminate
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn()) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn()).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //Right
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getColumn() + i > 8) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow(), myPosition.getColumn() + i) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow(), myPosition.getColumn() + i).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //Down
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn()) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn()).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //Left
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getColumn() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow(), myPosition.getColumn() - i) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow(), myPosition.getColumn() - i).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                        break;
                    } else {
                        break;
                    }
                }
            }
            return returnValue;
        }
        //Rook
        else{
            //up
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() + i > 8) {
                    break;  //if boundaries are left, terminate
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn()) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() + i, myPosition.getColumn()).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //Right
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getColumn() + i > 8) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow(), myPosition.getColumn() + i) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow(), myPosition.getColumn() + i).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + i), null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //Down
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getRow() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn()) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow() - i, myPosition.getColumn()).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                        break;
                    } else {
                        break;
                    }
                }
            }

            //Left
            for (int i = 1; i < 8; i++) {    //runs 7 times
                //Check to see if you have left the board
                if (myPosition.getColumn() - i < 1) {
                    break;
                }
                //check to see if there are pieces in the way
                if (board.getPiece(myPosition.getRow(), myPosition.getColumn() - i) == null) {
                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                }
                //check to see if that is an enemy piece, if not terminate this branch
                else {
                    if (board.getPiece(myPosition.getRow(), myPosition.getColumn() - i).getTeamColor() != PieceColor) {
                        returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - i), null));
                        break;
                    } else {
                        break;
                    }
                }
            }
            return returnValue;
        }
    }



//    public HashSet<ChessMove> CheckAndAddPosition(int Up, int Right, ChessPosition myPosition){ finnish this for the exam test
//        if(CheckInBounds(myPosition.getRow() + 2, myPosition.getColumn() + 1)){
//            //check to see if there are pieces in the way
//            if (board.getPiece(myPosition.getRow() + 2, myPosition.getColumn() + 1) == null) {
//                returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
//            }
//            //check to see if that is an enemy piece, if not terminate this branch
//            else {
//                if (board.getPiece(myPosition.getRow() + 2, myPosition.getColumn() + 1).getTeamColor() != PieceColor) {
//                    returnValue.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
//                }
//            }
//        }
//    }

    public boolean CheckInBounds(int row, int col){
            //Check to see if you have left the board
            if (row > 8 || col > 8 || row < 1 || col < 1) {
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
