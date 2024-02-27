package serviceTests;

import chess.ChessGame;
import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDAOMemory;
import dataAccess.UserDAOMemory;
import org.junit.jupiter.api.*;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import requests.*;
import results.*;
import server.Server;
import service.ClearService;
import service.GameService;
import service.UserService;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import dataAccess.*;

public class ServiceTest {
    //start memory
    private final AuthDAOMemory authDAO = new AuthDAOMemory();
    private final GameDAOMemory gameDAO = new GameDAOMemory();
    private final UserDAOMemory userDAO = new UserDAOMemory();
    //start Services
    ClearService clearService = new ClearService(authDAO, gameDAO, userDAO);
    GameService gameService = new GameService(authDAO, gameDAO);
    UserService userService = new UserService(authDAO, userDAO);

    String sampleAuth;

    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        clearService.clear(authDAO, gameDAO, userDAO);

        RegistrationResult kelec = userService.register(new RegistrationRequest("keleCant", "555", "kelecant@gmail.com"));
        RegistrationResult newUser = userService.register(new RegistrationRequest("newUser", "12345", "newuser@gmail.com"));
        userService.register(new RegistrationRequest("olduser", "54321", "olduser@gmail.com"));
        userService.logout(new LogoutRequest(kelec.authToken()));
        sampleAuth = newUser.authToken();

        GameService.createGameService(new CreateGameRequest(sampleAuth, "Game1"));
        GameService.createGameService(new CreateGameRequest(sampleAuth, "Game2"));
        GameService.createGameService(new CreateGameRequest(sampleAuth, "Game3"));
    }

    @Test
    @Order(1)
    @DisplayName("Clear Service")
    public void clearService() throws Exception {
        clearService.clear(authDAO, gameDAO, userDAO);

        AuthDAOMemory nauthDAO = new AuthDAOMemory();
        GameDAOMemory ngameDAO = new GameDAOMemory();
        UserDAOMemory nuserDAO = new UserDAOMemory();

        assertEquals(nauthDAO, authDAO, "Clear Service failed");
        assertEquals(ngameDAO, gameDAO, "Clear Service failed");
        assertEquals(nuserDAO, userDAO, "Clear Service failed");
    }

    @Test
    @Order(2)
    @DisplayName("Register Service")
    public void registerService() throws Exception {
        userService.register(new RegistrationRequest("stupid", "stupid1", "stupid@gmail.com"));
        assertEquals(4, userDAO.getMap().size(), "Addition not made");
        assertEquals(new UserData("stupid","stupid1", "stupid@gmail.com"), userDAO.getMap().get("stupid"));

    }

    @Test
    @Order(3)
    @DisplayName("Register Service Fail")
    public void registerServiceFail() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> userService.register(new RegistrationRequest(null, "stupid1", "stupid@gmail.com")));
        assertEquals("Error: bad request", exp.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Login Service")
    public void loginService() throws Exception {
        LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));

        assertEquals("keleCant", results.username());
    }

    @Test
    @Order(5)
    @DisplayName("Login Negative Service")
    public void loginNegativeService() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> UserService.login(new LoginRequest("keleCant", "5555")));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Logout Service")
    public void logoutService() throws Exception {
        LogoutResult results = UserService.logout(new LogoutRequest(sampleAuth));

        assertEquals(1,authDAO.getMap().size());
    }

    @Test
    @Order(7)
    @DisplayName("Logout Negative Service")
    public void logoutNegativeService() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> UserService.logout(new LogoutRequest("badAuth")));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("List Games Service")
    public void listgameService() throws Exception {
        ListGamesResult results = GameService.listGamesService(new ListGamesRequest(sampleAuth));
        assertEquals(3 ,gameDAO.getMap().size());
    }

    @Test
    @Order(9)
    @DisplayName("List Games Negative Service")
    public void listgameNegativeService() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.listGamesService(new ListGamesRequest("fakeauth")));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(10)
    @DisplayName("Create Game Service")
    public void createGameService() throws Exception {
        GameService.createGameService(new CreateGameRequest(sampleAuth, "Game4"));
        assertEquals(4 ,gameDAO.getMap().size());
    }

    @Test
    @Order(11)
    @DisplayName("Create Game Negative Service")
    public void createGameNegativeService() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.createGameService(new CreateGameRequest("BadAuth", "Game4")));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(12)
    @DisplayName("Join Game Service")
    public void joinGameSerive() throws Exception {
        LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
        CreateGameResult resultGameID = GameService.createGameService(new CreateGameRequest(results.authToken(), "Game4"));
        JoinGameResult joinGameResult = GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), results.authToken());
        assertEquals("keleCant" ,gameDAO.getMap().get(resultGameID.gameID()).whiteUsername());

    }

    @Test
    @Order(13)
    @DisplayName("Join Game Negative Service")
    public void joinGameNegativeService() throws Exception {
        LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
        CreateGameResult resultGameID = GameService.createGameService(new CreateGameRequest(results.authToken(), "Game4"));
        JoinGameResult joinGameResult = GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), results.authToken());

        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), "badAuth"));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(14)
    @DisplayName("Join Game Negative Service 2")
    public void joinGameNegativeService2() throws Exception {
        LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
        CreateGameResult resultGameID = GameService.createGameService(new CreateGameRequest(results.authToken(), "Game4"));
        JoinGameResult joinGameResult = GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), results.authToken());

        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.joinGameService(new JoinGameRequest(000, "WHITE"), results.authToken()));
        assertEquals("Error: bad request", exp.getMessage());
    }
}
