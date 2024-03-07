package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.SQLGameDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLGameDAOTest {
    GameDAO gameDAO = new SQLGameDAO();

    public SQLGameDAOTest() throws DataAccessException {
    }

    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        gameDAO.clear();
        gameDAO.createGame("F");
        gameDAO.createGame("U");
        gameDAO.createGame("N");

    }

    @Test
    @Order(1)
    @DisplayName("Clear Service")
    public void clearService() throws Exception {
        gameDAO.clear();
        assertEquals(false, gameDAO.doesGameExist(1));
    }
}
