package serviceTests;

import chess.ChessGame;
import dataAccess.AuthDAOMemory;
import dataAccess.GameDAOMemory;
import dataAccess.UserDAOMemory;
import org.junit.jupiter.api.*;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
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


public class ServiceTest {
    //start memory
    private final AuthDAOMemory authDAO = new AuthDAOMemory();
    private final GameDAOMemory gameDAO = new GameDAOMemory();
    private final UserDAOMemory userDAO = new UserDAOMemory();
    //start Services
    ClearService clearService = new ClearService(authDAO, gameDAO, userDAO);
    GameService gameService = new GameService(authDAO, gameDAO);
    UserService userService = new UserService(authDAO, userDAO);





    @Test
    @Order(1)
    @DisplayName("Clear Files")
    public void staticFiles() throws Exception {
//        String htmlFromServer = serverFacade.file("/").replaceAll("\r", "");
//        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
//                "Server response code was not 200 OK");
//        assertNotNull(htmlFromServer, "Server returned an empty file");
//        assertTrue(htmlFromServer.contains("CS 240 Chess Server Web API"));
    }
}
