package ui;

import model.*;

import java.util.Arrays;
import java.util.Scanner;

public class ClientMenu {
    AuthData authData;
    String identity = "Prelogin UI"; //"Postlogin UI" & "Gameplay UI" | "Exit"
    String userStatus = "LOGGED_OUT"; //"LOGGED_IN"
    private ServerFacade serverFacade;

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
        public void gamePlayClient () {

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

        void login (String input){
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



    void register(String input){
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




    void logout(){
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



    void create(String input){
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




    void list(){
        System.out.println("listing games");
        try {
            GameData[] gameData = serverFacade.makeRequest("GET", "/game", null, GameData[].class, authData.authToken());

            for (int i = 0; Arrays.stream(gameData).count() > i; i++) {
                System.out.println("GameID:" + gameData[i].gameID() + " Game Name:" + gameData[i].gameName());
                System.out.println("   White user:" + gameData[i].whiteUsername());
                System.out.println("   Black user:" + gameData[i].blackUsername());
            }

        } catch (Exception exeption) {
            returnErrorMessage(exeption.getMessage());;
        }

    }




    void join(String input){
        System.out.println("joining game");

        //cut up input
        String[] inputData = input.split(" ");


    }





    void observe(String input){
        System.out.println("observing");

        //cut up input
        String[] inputData = input.split(" ");


    }
}