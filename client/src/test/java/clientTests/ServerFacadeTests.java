package clientTests;

import dataAccess.*;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ClientMenu;
import ui.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {
    GameDAO gameDAO = new SQLGameDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    UserDAO userDAO = new SQLUserDAO();
    private static Server server;
    private static ServerFacade serverFacade;
    private static ClientMenu clientMenu;

    public ServerFacadeTests() throws DataAccessException {
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
        clientMenu = new ClientMenu(serverFacade);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
        clientMenu.register("Register user1 password email");
        clientMenu.register("Register user2 apples email");
    }


    @Test
    @Order(1)
    @DisplayName("Client Register")
    public void clientRegistar() throws Exception {
        clientMenu.register("Register KeleCant SucksToSuck email");
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> userDAO.checkUsername("KeleCant"));
        assertEquals("Error: already taken", exp.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("Negative Client Register")
    public void negativeClientRegistar() throws Exception {
        clientMenu.register("Register KeleCant SucksToSuck");
        userDAO.checkUsername("KeleCant");  //no exeption thrown if the username is not recorded
    }

    @Test
    @Order(3)
    @DisplayName("Client Login")
    public void loginService() throws Exception {
        clientMenu.register("Register KeleCant SucksToSuck email");
        clientMenu.logout();
        clientMenu.login("login KeleCant SucksToSuck");
        assertEquals("KeleCant",clientMenu.authData.username());
    }

    @Test
    @Order(4)
    @DisplayName("Negative Client Login")
    public void negativeLoginService() throws Exception {
        //the register function contains the login function within it
        clientMenu.register("Register KeleCant SucksToSuck email");
        String auth1 = clientMenu.authData.username();
        clientMenu.register("Register Kelet SucksToSuck email");
        assertNotEquals(auth1, clientMenu.authData.username());
        clientMenu.logout();
        clientMenu.login("login Kelet SucksToSuck");
        assertNotEquals(auth1, clientMenu.authData.username());
    }

    @Test
    @Order(5)
    @DisplayName("Client Logout")
    public void clientLogout() throws Exception {
        clientMenu.register("Register KeleCant SucksToSuck email");
        clientMenu.logout();
        assertEquals(authDAO.getAuth(clientMenu.authData.authToken()), false, "Auth not detected");
    }

    @Test
    @Order(6)
    @DisplayName("Negative Client Logout")
    public void negativeClientLogout() throws Exception {
        clientMenu.register("Register KeleCant SucksToSuck email");
        clientMenu.logout();
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.getUsername(clientMenu.authData.authToken()));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Client Create")
    public void clientCreate() throws Exception {
        clientMenu.create("create theGame");
        assertEquals(true, gameDAO.doesGameExist(1));
    }

    @Test
    @Order(8)
    @DisplayName("Negative Client Create")
    public void negativeClientCreate() throws Exception {
        clientMenu.create("create");
        assertEquals(false, gameDAO.doesGameExist(1), "game not read properly");
    }

    @Test
    @Order(10)
    @DisplayName("Client list")
    public void clientList() throws Exception {
        clientMenu.create("create game");
        clientMenu.create("create game2");
        clientMenu.create("create game3");
        assertEquals(3, gameDAO.listGame().size(), "Size is incorrect");
        clientMenu.list();
    }

    @Test
    @Order(11)
    @DisplayName("Client Join")
    public void joinGameSerive() throws Exception {
        clientMenu.create("create game");
        assertDoesNotThrow(() -> clientMenu.join("join 1 BLACK"));
    }

    @Test
    @Order(12)
    @DisplayName("Negative Client Join")
    public void joinGameNegativeService() throws Exception {
        clientMenu.create("create game");
        clientMenu.join("join 1 BLACK");
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> gameDAO.updateGame(clientMenu.authData.authToken(), 1, "BLACK", authDAO));
        assertEquals("Error: already taken", exp.getMessage());
    }

    @Test
    @Order(13)
    @DisplayName("Client Observe")
    public void clientObserve() throws Exception {
        clientMenu.create("create game");
        clientMenu.observe("observe 1");
    }

}

