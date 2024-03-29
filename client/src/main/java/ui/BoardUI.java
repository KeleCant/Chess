package ui;

import chess.ChessGame;
import chess.ChessPiece;
import model.GameData;

public class BoardUI {
    private GameData gameData;

    public BoardUI(GameData gameData){
        this.gameData = gameData;
    }

    public void displayWhite(){
        String[][] stringBoard = stringlist(gameData.game().getBoard().getGameBoard());

        //top row
        System.out.println(EscapeSequences.EMPTY+ " A" + EscapeSequences.EMPTY + "B" + EscapeSequences.EMPTY + "C" + EscapeSequences.EMPTY + "D" + EscapeSequences.EMPTY + "E" + EscapeSequences.EMPTY + "F" + EscapeSequences.EMPTY + "G" + EscapeSequences.EMPTY + "H");
        for (int i = 7; i >= 0; i--){
            System.out.print(i + 1 + " |");
            for (int j = 7; j >= 0; j--){
                System.out.print(stringBoard[i][j] + "|");
            }
            System.out.print("\n");
        }
    }

    public void displayBlack(){
        String[][] stringBoard = stringlist(gameData.game().getBoard().getGameBoard());

        //top row
        System.out.println(EscapeSequences.EMPTY+ " H" + EscapeSequences.EMPTY + "G" + EscapeSequences.EMPTY + "F" + EscapeSequences.EMPTY + "E" + EscapeSequences.EMPTY + "D" + EscapeSequences.EMPTY + "C" + EscapeSequences.EMPTY + "B" + EscapeSequences.EMPTY + "A");
        for (int i = 0; i < 8; i++){
            System.out.print(i+1 + " |");
            for (int j = 0; j < 8; j++){
                System.out.print(stringBoard[i][j] + "|");
            }
            System.out.print("\n");
        }
    }

    private String[][] stringlist(ChessPiece[][] gameBoard){
        String[][] returnBoard = new String[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (gameBoard[i][j] == null)
                    returnBoard[i][j] = EscapeSequences.EMPTY;
                else
                    returnBoard[i][j] = setString(gameBoard[i][j]);
            }
        }
        return returnBoard;
    }

    private String setString(ChessPiece piece){
        //for white
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            if (piece.getPieceType() == ChessPiece.PieceType.KING)
                return EscapeSequences.WHITE_KING;
            if (piece.getPieceType() == ChessPiece.PieceType.QUEEN)
                return EscapeSequences.WHITE_QUEEN;
            if (piece.getPieceType() == ChessPiece.PieceType.ROOK)
                return EscapeSequences.WHITE_ROOK;
            if (piece.getPieceType() == ChessPiece.PieceType.BISHOP)
                return EscapeSequences.WHITE_BISHOP;
            if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT)
                return EscapeSequences.WHITE_KNIGHT;
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN)
                return EscapeSequences.WHITE_PAWN;
        } else {
            if (piece.getPieceType() == ChessPiece.PieceType.KING)
                return EscapeSequences.BLACK_KING;
            if (piece.getPieceType() == ChessPiece.PieceType.QUEEN)
                return EscapeSequences.BLACK_QUEEN;
            if (piece.getPieceType() == ChessPiece.PieceType.ROOK)
                return EscapeSequences.BLACK_ROOK;
            if (piece.getPieceType() == ChessPiece.PieceType.BISHOP)
                return EscapeSequences.BLACK_BISHOP;
            if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT)
                return EscapeSequences.BLACK_KNIGHT;
            if (piece.getPieceType() == ChessPiece.PieceType.PAWN)
                return EscapeSequences.BLACK_PAWN;
        }
        return EscapeSequences.EMPTY;
    }


}
