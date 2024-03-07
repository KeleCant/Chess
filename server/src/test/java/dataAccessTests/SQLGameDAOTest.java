package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLGameDAOTest {
    GameDAO gameDAO = new SQLGameDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    int testID;
    String testAuth;

    public SQLGameDAOTest() throws DataAccessException {
    }

    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        gameDAO.clear();
        gameDAO.createGame("F");
        gameDAO.createGame("U");
        testID = gameDAO.createGame("N");
        testAuth = authDAO.createAuth("Sam");

    }

    @Test
    @Order(1)
    @DisplayName("Clear")
    public void clear() throws Exception {
        gameDAO.clear();
        assertEquals(false, gameDAO.doesGameExist(1));
    }

    @Test
    @Order(2)
    @DisplayName("updateGame")
    public void updateGame() throws Exception {
        assertDoesNotThrow(() -> gameDAO.updateGame(testAuth, testID, "WHITE", authDAO));
        assertDoesNotThrow(() -> gameDAO.updateGame(testAuth, testID, "BLACK", authDAO));
        assertDoesNotThrow(() -> gameDAO.updateGame(testAuth, testID, null, authDAO));
    }

    @Test
    @Order(3)
    @DisplayName("updateGameNegative")
    public void updateGameNegative() throws Exception {
        assertDoesNotThrow(() -> gameDAO.updateGame(testAuth, testID, "BLACK", authDAO));
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> gameDAO.updateGame(testAuth, testID, "BLACK", authDAO));
        assertEquals("Error: already taken", exp.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("doesGameExist")
    public void doesgameExist() throws Exception {
        assertEquals(true, gameDAO.doesGameExist(testID), "game not read properly");
    }

    @Test
    @Order(5)
    @DisplayName("doesGameExistNegative")
    public void doesgameExistNegative() throws Exception {
        assertEquals(false, gameDAO.doesGameExist(0), "Game read improperluy");
    }

    @Test
    @Order(6)
    @DisplayName("listgames")
    public void listgames() throws Exception {
        assertEquals(3, gameDAO.listGame().size(), "Size is incorrect");
    }
}
