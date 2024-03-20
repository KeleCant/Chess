import chess.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        //System.out.println("Opening Server Facade");
        ServerFacade serverFacade = new ServerFacade(2022);
        ClientMenu cMenu = new ClientMenu(serverFacade);
        //System.out.println("Starting Client");
        cMenu.run();
    }
}