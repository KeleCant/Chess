package passoffTests.serverTests;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import server.Server;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FailedTestsOnly {

    private static TestModels.TestUser existingUser;

    private static TestModels.TestUser newUser;

    private static TestModels.TestCreateRequest createRequest;

    private static TestServerFacade serverFacade;
    private static Server server;

    private String existingAuth;


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new TestServerFacade("localhost", Integer.toString(port));

        existingUser = new TestModels.TestUser();
        existingUser.username = "ExistingUser";
        existingUser.password = "existingUserPassword";
        existingUser.email = "eu@mail.com";

        newUser = new TestModels.TestUser();
        newUser.username = "NewUser";
        newUser.password = "newUserPassword";
        newUser.email = "nu@mail.com";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";
    }


    @BeforeEach
    public void setup() throws TestException {
        serverFacade.clear();

        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = existingUser.password;
        registerRequest.email = existingUser.email;

        //one user already logged in
        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
        existingAuth = regResult.authToken;
    }

    @Test
    @Order(1)
    @DisplayName("Unique Authtoken Each Login")
    public void uniqueAuthorizationTokens() throws TestException {
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;

        TestModels.TestLoginRegisterResult loginOne = serverFacade.login(loginRequest);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertNotNull(loginOne.authToken, "Login result did not contain an authToken");

        TestModels.TestLoginRegisterResult loginTwo = serverFacade.login(loginRequest);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertNotNull(loginTwo.authToken, "Login result did not contain an authToken");

        Assertions.assertNotEquals(existingAuth, loginOne.authToken,
                "Authtoken returned by login matched authtoken from prior register");
        Assertions.assertNotEquals(existingAuth, loginTwo.authToken,
                "Authtoken returned by login matched authtoken from prior register");
        Assertions.assertNotEquals(loginOne.authToken, loginTwo.authToken,
                "Authtoken returned by login matched authtoken from prior login");


        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertFalse(
                createResult.message != null && createResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");


        TestModels.TestResult logoutResult = serverFacade.logout(existingAuth);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertFalse(
                logoutResult.message != null && logoutResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response gave an error message");


        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;

        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, loginOne.authToken);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertFalse(
                joinResult.message != null && joinResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");


        TestModels.TestListResult listResult = serverFacade.listGames(loginTwo.authToken);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertFalse(
                listResult.message != null && listResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response gave an error message");
        Assertions.assertEquals(1, listResult.games.length);
        Assertions.assertEquals(existingUser.username, listResult.games[0].whiteUsername);
    }

    @Test
    @Order(2)
    @DisplayName("Join Created Game")
    public void goodJoin() throws TestException {
        //create game
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        //join as white
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;

        //try join
        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, existingAuth);

        //check
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Assertions.assertFalse(
                joinResult.message != null && joinResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");

        TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);

        Assertions.assertEquals(1, listResult.games.length);
        Assertions.assertEquals(existingUser.username, listResult.games[0].whiteUsername);
        Assertions.assertNull(listResult.games[0].blackUsername);
    }

    @Test
    @Order(3)
    @DisplayName("Join Bad Team Color")
    public void badColorJoin() throws TestException {
        //create game
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        //add existing user as black
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        serverFacade.verifyJoinPlayer(joinRequest, existingAuth);

        //register second user
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = newUser.username;
        registerRequest.password = newUser.password;
        registerRequest.email = newUser.email;
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        //join request trying to also join  as black
        joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, registerResult.authToken);

        //check failed
        Assertions.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, serverFacade.getStatusCode(),
                "Server response code was not 403 Forbidden");
        Assertions.assertTrue(
                joinResult.message != null && joinResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Invalid Request didn't return an error message");
    }

    @Test
    @Order(4)
    @DisplayName("List Multiple Games")
    public void gamesList() throws TestException {
        //register a few users to create games
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "a";
        registerRequest.password = "A";
        registerRequest.email = "a.A";
        TestModels.TestLoginRegisterResult userA = serverFacade.register(registerRequest);

        registerRequest.username = "b";
        registerRequest.password = "B";
        registerRequest.email = "b.B";
        TestModels.TestLoginRegisterResult userB = serverFacade.register(registerRequest);

        registerRequest.username = "c";
        registerRequest.password = "C";
        registerRequest.email = "c.C";
        TestModels.TestLoginRegisterResult userC = serverFacade.register(registerRequest);

        //create games

        //1 as black from A
        createRequest.gameName = "I'm numbah one!";
        TestModels.TestCreateResult game1 = serverFacade.createGame(createRequest, userA.authToken);

        //1 as white from B
        createRequest.gameName = "Lonely";
        TestModels.TestCreateResult game2 = serverFacade.createGame(createRequest, userB.authToken);

        //1 of each from C
        createRequest.gameName = "GG";
        TestModels.TestCreateResult game3 = serverFacade.createGame(createRequest, userC.authToken);
        createRequest.gameName = "All by myself";
        TestModels.TestCreateResult game4 = serverFacade.createGame(createRequest, userC.authToken);

        //A join game 1 as black
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game1.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userA.authToken);

        //B join game 2 as white
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game2.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userB.authToken);

        //C join game 3 as white
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game3.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userC.authToken);

        //A join game3 as black
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game3.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userA.authToken);

        //C play self in game 4
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game4.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userC.authToken);
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game4.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userC.authToken);

        //create expected entry items
        Collection<TestModels.TestListResult.TestListEntry> expectedList = new HashSet<>();

        //game 1
        TestModels.TestListResult.TestListEntry entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game1.gameID;
        entry.gameName = "I'm numbah one!";
        entry.blackUsername = userA.username;
        entry.whiteUsername = null;
        expectedList.add(entry);

        //game 2
        entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game2.gameID;
        entry.gameName = "Lonely";
        entry.blackUsername = null;
        entry.whiteUsername = userB.username;
        expectedList.add(entry);

        //game 3
        entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game3.gameID;
        entry.gameName = "GG";
        entry.blackUsername = userA.username;
        entry.whiteUsername = userC.username;
        expectedList.add(entry);

        //game 4
        entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game4.gameID;
        entry.gameName = "All by myself";
        entry.blackUsername = userC.username;
        entry.whiteUsername = userC.username;
        expectedList.add(entry);

        //list games
        TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK");
        Collection<TestModels.TestListResult.TestListEntry> returnedList =
                new HashSet<>(Arrays.asList(listResult.games));

        //check
        Assertions.assertEquals(expectedList, returnedList, "Returned Games list was incorrect");
    }
}
