package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import requests.JoinGameRequest;
import results.ListGamesResult;
import webSocket.webSocketClient;
import webSocketMessages.userCommands.*;
import dataAccess.DataAccessException;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ClientMenu {
    public AuthData authData;
    GameData currentGame;
    String identity = "Prelogin UI"; //"Postlogin UI" & "Gameplay UI" | "Exit"
    String userStatus = "LOGGED_OUT"; //"LOGGED_IN"

    private ServerFacade serverFacade;
    private webSocketClient webSocketClient;

    String currentColor = "NULL";
    ChessGame.TeamColor teamColor;

    public ClientMenu(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }

    public void run() {
        //clear the screen
        System.out.println(EscapeSequences.ERASE_SCREEN);
        System.out.println("Welcome to Chess");
        System.out.println("Type \"Help\" for a list of commands or \"Quit\" to exit the program.");

        //listen for an input
        while (identity != "Exit") {
            System.out.print("[" + userStatus + "] >>>> ");
            if (identity == "Prelogin UI")
                preLoginClient();
            else if (identity == "Postlogin UI")
                postLoginClient();
            else if (identity == "Gameplay UI")
                gamePlayClient();
        }
        System.out.print("Exiting Program");
    }

    //Help, Quit, Login, Register
    public void preLoginClient() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.contains("Help") || input.contains("help")) {
            System.out.println("Register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
            System.out.println("Login <USERNAME> <PASSWORD> - to play chess");
            System.out.println("Quit - to exit the program");
            System.out.println("Help - list possible commands");
        } else if (input.contains("Quit") || input.contains("quit")) {
            identity = "Exit";
        } else if (input.contains("Login") || input.contains("login")) {
            login(input);
        } else if (input.contains("Register") || input.contains("register")) {
            register(input);
        } else {
            System.out.println("Invalid Input - Type \"Help\" for a list of commands or \"Quit\" to exit the program.");
        }
    }

        //Help, Create Game, List Games, Join Game, Join Observer
        public void postLoginClient () {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.contains("Help") || input.contains("help")) {
                System.out.println("Create <GAMENAME> - Creates a new Game");
                System.out.println("list - Lists Every Game");
                System.out.println("Join <ID> [WHITE|BLACK|<empty>] - Joins a game");
                System.out.println("Observe <ID> - Joins game as a spectator");
                System.out.println("Logout - Log out of your account");
                System.out.println("Quit - closes the program");
                System.out.println("Help - list possible commands");
            } else if (input.contains("Logout") || input.contains("logout")) {
                logout();
            } else if (input.contains("Create") || input.contains("create")) {
                create(input);
            } else if (input.contains("List") || input.contains("list")) {
                list();
            } else if (input.contains("Join") || input.contains("join")) {
                join(input);
            } else if (input.contains("Observe") || input.contains("observe")) {
                observe(input);
            } else if (input.contains("Quit") || input.contains("quit")) {
                logout();
                identity = "Exit";
            } else {
                System.out.println("Invalid Input - Type \"Help\" for a list of commands or \"Quit\" to exit the program.");
            }
        }

        //
        public void gamePlayClient () throws Exception {
            //join game message
            System.out.println("Now Displaying Game(" + currentGame.gameID() + "): " + currentGame.gameName());
            if (!currentColor.contains("NULL")){
                System.out.println("Welcome " + authData.username() + " you are currently playing as " + currentColor);
            } else {
                System.out.println("Welcome " + authData.username() + " you are currently Observing");
            }
            System.out.println("Now Displaying Game(" + currentGame.gameID() + "): " + currentGame.gameName());


//            System.out.print("[" + userStatus + "] >>>> ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.contains("Help") || input.contains("help")) {
                System.out.println("Redraw - Redraws the chess board");
                System.out.println("Leave - returns user to lobby");
                System.out.println("Move <Start Position> <End Position> - makes a move");
                System.out.println("Resign - The user forfeits the game and the game is over");
                System.out.println("Highlight - Shows legal moves");
                System.out.println("Help - list possible commands");
            }  else if (input.contains("Redraw") || input.contains("redraw")) {
                redraw();
            }  else if (input.contains("Leave") || input.contains("leave")) {
                leave();
            }  else if (input.contains("Move") || input.contains("move")) {
                move(input);
            }  else if (input.contains("Resign") || input.contains("resign")) {
                resign();
            }  else if (input.contains("Highlight") || input.contains("highlight")) {
                highlight();
            } else {
                System.out.println("Invalid Input - Type \"Help\" for a list of commands or \"Quit\" to exit the program.");
            }






            System.out.println("Now Displaying Game(" + currentGame.gameID() + "): " + currentGame.gameName());
            //Display the Board
            BoardUI display = new BoardUI(currentGame);
            if (currentGame.whiteUsername()!=null){
                if (currentGame.whiteUsername().contains(authData.username())){
                    display.displayWhite();
                }
            } else if (currentGame.blackUsername()!=null) {
                if (currentGame.blackUsername().contains(authData.username())){
                    display.displayBlack();
                }
            } else if (!currentGame.blackUsername().contains(authData.username()) & currentGame.whiteUsername().contains(authData.username())) {
                display.displayWhite();
                System.out.print("\n");
                display.displayBlack();
            }
        }



        private void returnErrorMessage (String errorCode){
            if (errorCode.contains("400"))
                System.out.println("Error: bad request");
            else if (errorCode.contains("401"))
                System.out.println("Error: unauthorized");
            else if (errorCode.contains("403"))
                System.out.println("Error: already taken");
            else if (errorCode.contains("500"))
                System.out.println("Error: bad request");
            else
                System.out.println("Error: " + errorCode);
        }



        public void login (String input){
            System.out.println("Logging in");

            //cut up input
            String[] inputData = input.split(" ");

            if (Arrays.stream(inputData).count() == 3) {
                try {
                    authData = serverFacade.makeRequest("POST", "/session", new UserData(inputData[1], inputData[2], null), AuthData.class, null);
                    System.out.println("Logged in as " + inputData[1]);

                    userStatus = "LOGGED_IN";
                    identity = "Postlogin UI";
                } catch (Exception exeption) {
                    returnErrorMessage(exeption.getMessage());
                }
            } else {
                System.out.println("Invalid Input: Login <USERNAME> <PASSWORD>");
            }
        }



    public void register(String input){
        System.out.println("Registering User");

        //cut up input
        String[] inputData = input.split(" ");

        if (Arrays.stream(inputData).count() == 4) {
            try {
                authData = serverFacade.makeRequest("POST", "/user", new UserData(inputData[1], inputData[2], inputData[3]), AuthData.class, null);
                System.out.println("Logged in as " + inputData[1]);

                userStatus = "LOGGED_IN";
                identity = "Postlogin UI";
            } catch (Exception exeption) {
                returnErrorMessage(exeption.getMessage());;
            }
        } else {
            System.out.println("Invalid Input: Register <USERNAME> <PASSWORD> <EMAIL>");
        }
    }



    public void logout(){
        System.out.println("Logging out");

        try {
            serverFacade.makeRequest("DELETE", "/session", null, null, authData.authToken());
            System.out.println("Logged Out");

            userStatus = "LOGGED_OUT";
            identity = "Prelogin UI";
        } catch (Exception exeption) {
            returnErrorMessage(exeption.getMessage());;
        }
    }



    public void create(String input){
        System.out.println("creating game");


        //cut up input
        String[] inputData = input.split(" ");


        if (Arrays.stream(inputData).count() == 2) {
            try {
                GameData gameData = serverFacade.makeRequest("POST", "/game", new GameData(0, null, null, inputData[1], null), GameData.class, authData.authToken());
                System.out.println("new Game Created:" + inputData[1]);
                System.out.println("Game ID:" + gameData.gameID());
            } catch (Exception exeption) {
                returnErrorMessage(exeption.getMessage());;
            }
        } else {
            System.out.println("Invalid Input: Create <GAMENAME>");
        }
    }



    public void list(){
        System.out.println("listing games");
        try {
            ListGamesResult gameData = serverFacade.makeRequest("GET", "/game", null, ListGamesResult.class, authData.authToken());

            for (GameData thisGame : gameData.games()){
                System.out.println("GameID:" + thisGame.gameID() + " Game Name:" + thisGame.gameName());
                System.out.println("   White user:" + thisGame.whiteUsername());
                System.out.println("   Black user:" + thisGame.blackUsername());
            }

        } catch (Exception exeption) {
            //throw new RuntimeException(exeption);
            returnErrorMessage(exeption.getMessage());
        }

    }



    public void join(String input){
        System.out.println("Checking Game ID");

        //cut up input
        String[] inputData = input.split(" ");


        if (Arrays.stream(inputData).count() == 3 && (inputData[2].contains("WHITE") || inputData[2].contains("BLACK"))) {
            try {
                serverFacade.makeRequest("PUT", "/game", new JoinGameRequest(parseInt(inputData[1]), inputData[2]), null, authData.authToken());
                System.out.println(authData.username() + " Joined Game as " + inputData[2]);
                //identity = "Gameplay UI";

                ListGamesResult gameData = serverFacade.makeRequest("GET", "/game", null, ListGamesResult.class, authData.authToken());

                for (GameData thisGame : gameData.games()){
                    if (thisGame.gameID() == parseInt(inputData[1]))
                        currentGame = thisGame;
                }

                //Set game position
                if (inputData[2].contains("WHITE")){
                    currentColor = "WHITE";
                    teamColor = ChessGame.TeamColor.WHITE;
                } else if (inputData[2].contains("BLACK")){
                    currentColor = "BLACK";
                    teamColor = ChessGame.TeamColor.BLACK;
                } else {
                    currentColor = "NULL";
                }

                identity = "Gameplay UI";

                //fixme Establish websocket connection
                BoardUI display = new BoardUI(currentGame);
                webSocketClient = new webSocketClient(serverFacade.getServerID(), display, currentColor);
                webSocketClient.send(new Gson().toJson(new JoinPlayerMessage(authData.authToken(),parseInt(inputData[1]), teamColor)));

            } catch (Exception exeption) {
                returnErrorMessage(exeption.getMessage());;
            }
        } else if (Arrays.stream(inputData).count() == 2){
            try {
                serverFacade.makeRequest("PUT", "/game", new JoinGameRequest(parseInt(inputData[1]), null), null, authData.authToken());
                System.out.println("Game ID is Valid");
            } catch (Exception exeption) {
                returnErrorMessage(exeption.getMessage());;
            }
        } else {
            System.out.println("Invalid Input: Join <ID> [WHITE|BLACK|<empty>]");
        }
    }



    public void observe(String input){
        System.out.println("observing");

        //cut up input
        String[] inputData = input.split(" ");

        if (Arrays.stream(inputData).count() == 2) {
            //Check to see if Game ID exits
            try {
                serverFacade.makeRequest("PUT", "/game", new JoinGameRequest(parseInt(inputData[1]), null), null, authData.authToken());

            } catch (Exception exeption) {
                returnErrorMessage(exeption.getMessage());;
            }

            try {
                ListGamesResult gameData = serverFacade.makeRequest("GET", "/game", null, ListGamesResult.class, authData.authToken());

                for (GameData thisGame : gameData.games()){
                    if (thisGame.gameID() == parseInt(inputData[1]))
                        currentGame = thisGame;
                }

                identity = "Gameplay UI";
                //fixme Establish websocket connection
                currentColor = "Null";
                BoardUI display = new BoardUI(currentGame);
                webSocketClient = new webSocketClient(serverFacade.getServerID(), display, currentColor);
                webSocketClient.send(new Gson().toJson(new JoinObserverMessage(authData.authToken(),parseInt(inputData[1]))));



            } catch (Exception exeption) {
                returnErrorMessage(exeption.getMessage());;
            }
        } else {
            System.out.println("Invalid Input: Observe <ID>");
        }
    }





    //
    //game INTERFACE
    //

    private void redraw(){
        webSocketClient.redrawBoard();
    }

    private void leave() throws Exception {
        webSocketClient.send(new Gson().toJson(new LeaveMessage(authData.authToken(), currentGame.gameID())));
    }

    private void move(String input) throws Exception {
        //break up string inputData[1] start Pos, inputData[2] end pos, inputData[3] promotion piece
        String[] inputData = input.split(" ");

        //validate input
        if (inputData.length < 3 && inputData.length > 4){
            System.out.println("Invalid input, it needs to have at least 3 characters");
            System.out.println("Move <Start Position> <End Position> <Promotion Piece> - makes a move");
        } else {
            //convert String to Start
            try {
                ChessPosition startPos = positionConverter(inputData[1]);
                ChessPosition endPos = positionConverter(inputData[2]);
                ChessPiece.PieceType promtionPiece = null;
                if (inputData.length == 4)
                    promtionPiece = pieceConverter(inputData[3]);

                ChessMove newMove = new ChessMove(startPos, endPos, promtionPiece);

                //test move to see if its valid
                if (currentGame.game().validMoves(startPos).contains(endPos)) {
                    webSocketClient.send(new Gson().toJson(new MakeMoveMessage(authData.authToken(), newMove, currentGame.gameID())));
                } else {
                    System.out.println("This move is invalid");
                }

            } catch(Exception e) {
                System.out.println("Invalid input, it needs to have at least 3 characters");
                System.out.println("Move <Start Position> <End Position> <Promotion Piece> - makes a move");
                System.out.println("<Start Position> and <End Position> must contain a number 1-8 and a leter A-B");
                System.out.println("Example: Move 2A 3A");
            }
        }
    }

    private void resign() throws Exception {
        webSocketClient.send(new Gson().toJson(new ResignMessage(authData.authToken(), currentGame.gameID())));
    }

    private void highlight(){
        webSocketClient.highlightMoves();
    }

    private ChessPiece.PieceType pieceConverter(String input){
        if (input.contains("QUEEN"))
            return ChessPiece.PieceType.QUEEN;
        else if (input.contains(("ROOK")))
            return ChessPiece.PieceType.ROOK;
        else if (input.contains(("BISHOP")))
            return ChessPiece.PieceType.BISHOP;
        else if (input.contains(("KNIGHT")))
            return ChessPiece.PieceType.KNIGHT;
        else
            return null;
    }

    private ChessPosition positionConverter(String input) throws DataAccessException {
        int row;
        int col;

        if (input.contains("1"))
            row = 1;
        else if (input.contains("2"))
            row = 2;
        else if (input.contains("3"))
            row = 3;
        else if (input.contains("4"))
            row = 4;
        else if (input.contains("5"))
            row = 5;
        else if (input.contains("6"))
            row = 6;
        else if (input.contains("7"))
            row = 7;
        else if (input.contains("8"))
            row = 8;
        else
            throw new DataAccessException("No Letter Found")

            if (input.contains("A"))
                col = 1;
            else if (input.contains("B"))
                col = 2;
            else if (input.contains("C"))
                col = 3;
            else if (input.contains("D"))
                col = 4;
            else if (input.contains("E"))
                col = 5;
            else if (input.contains("F"))
                col = 6;
            else if (input.contains("G"))
                col = 7;
            else if (input.contains("H"))
                col = 8;
            else
                throw new DataAccessException("No Letter Found");


        return new ChessPosition(row, col);
    }
}
