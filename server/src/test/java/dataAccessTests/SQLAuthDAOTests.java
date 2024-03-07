package dataAccessTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SQLAuthDAOTests {
    AuthDAO authDAO = new SQLAuthDAO();

    public SQLAuthDAOTests() throws DataAccessException {
    }

    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        authDAO.clear();
        authDAO.createAuth("Johny");
        authDAO.createAuth("Bill");
        authDAO.createAuth("Sam");
    }

    @Test
    @Order(1)
    @DisplayName("Clear Service")
    public void clearService() throws Exception {
        authDAO.clear();
        AuthDAO nauthDAO = new SQLAuthDAO();
        assertEquals(nauthDAO, authDAO, "Clear Service failed");
    }

        @Test
        @Order(2)
        @DisplayName("createAuth")
        public void createAuth() throws Exception {
            String key1 = authDAO.createAuth("henry");
            String key2 = authDAO.createAuth("larry");
            assertNotEquals(key1, key2, "Auths are not unique");

        }

        @Test
        @Order(3)
        @DisplayName("createAuth Fail")
        public void registerServiceFail() throws Exception {
            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAuth("Johny"));
            assertEquals("Error: bad request", exp.getMessage());
        }
//
//        @Test
//        @Order(4)
//        @DisplayName("Login Service")
//        public void loginService() throws Exception {
//            LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
//
//            assertEquals("keleCant", results.username());
//        }
//
//        @Test
//        @Order(5)
//        @DisplayName("Login Negative Service")
//        public void loginNegativeService() throws Exception {
//            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> UserService.login(new LoginRequest("keleCant", "5555")));
//            assertEquals("Error: unauthorized", exp.getMessage());
//        }
//
//        @Test
//        @Order(6)
//        @DisplayName("Logout Service")
//        public void logoutService() throws Exception {
//            LogoutResult results = UserService.logout(new LogoutRequest(sampleAuth));
//
//            assertEquals(1,authDAO.getMap().size());
//        }
//
//        @Test
//        @Order(7)
//        @DisplayName("Logout Negative Service")
//        public void logoutNegativeService() throws Exception {
//            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> UserService.logout(new LogoutRequest("badAuth")));
//            assertEquals("Error: unauthorized", exp.getMessage());
//        }
//
//        @Test
//        @Order(8)
//        @DisplayName("List Games Service")
//        public void listgameService() throws Exception {
//            ListGamesResult results = GameService.listGamesService(new ListGamesRequest(sampleAuth));
//            assertEquals(3 ,gameDAO.getMap().size());
//        }
//
//        @Test
//        @Order(9)
//        @DisplayName("List Games Negative Service")
//        public void listgameNegativeService() throws Exception {
//            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.listGamesService(new ListGamesRequest("fakeauth")));
//            assertEquals("Error: unauthorized", exp.getMessage());
//        }
//
//        @Test
//        @Order(10)
//        @DisplayName("Create Game Service")
//        public void createGameService() throws Exception {
//            GameService.createGameService(new CreateGameRequest(sampleAuth, "Game4"));
//            assertEquals(4 ,gameDAO.getMap().size());
//        }
//
//        @Test
//        @Order(11)
//        @DisplayName("Create Game Negative Service")
//        public void createGameNegativeService() throws Exception {
//            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.createGameService(new CreateGameRequest("BadAuth", "Game4")));
//            assertEquals("Error: unauthorized", exp.getMessage());
//        }
//
//        @Test
//        @Order(12)
//        @DisplayName("Join Game Service")
//        public void joinGameSerive() throws Exception {
//            LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
//            CreateGameResult resultGameID = GameService.createGameService(new CreateGameRequest(results.authToken(), "Game4"));
//            JoinGameResult joinGameResult = GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), results.authToken());
//            assertEquals("keleCant" ,gameDAO.getMap().get(resultGameID.gameID()).whiteUsername());
//
//        }
//
//        @Test
//        @Order(13)
//        @DisplayName("Join Game Negative Service")
//        public void joinGameNegativeService() throws Exception {
//            LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
//            CreateGameResult resultGameID = GameService.createGameService(new CreateGameRequest(results.authToken(), "Game4"));
//            JoinGameResult joinGameResult = GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), results.authToken());
//
//            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), "badAuth"));
//            assertEquals("Error: unauthorized", exp.getMessage());
//        }
//
//        @Test
//        @Order(14)
//        @DisplayName("Join Game Negative Service 2")
//        public void joinGameNegativeService2() throws Exception {
//            LoginResult results = UserService.login(new LoginRequest("keleCant", "555"));
//            CreateGameResult resultGameID = GameService.createGameService(new CreateGameRequest(results.authToken(), "Game4"));
//            JoinGameResult joinGameResult = GameService.joinGameService(new JoinGameRequest(resultGameID.gameID(), "WHITE"), results.authToken());
//
//            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> GameService.joinGameService(new JoinGameRequest(000, "WHITE"), results.authToken()));
//            assertEquals("Error: bad request", exp.getMessage());
//        }
    }

