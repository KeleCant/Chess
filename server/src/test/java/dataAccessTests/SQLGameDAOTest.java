package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import passoffTests.testClasses.TestException;

public class SQLGameDAOTest {
    AuthDAO authDAO = new SQLAuthDAO();
    GameDAO gameDAO = new SQLGameDAO();
    UserDAO userDAO = new SQLUserDAO();

    public void SQLAuthDAOTests() throws DataAccessException {
    }

    public SQLGameDAOTest() throws DataAccessException {
    }

    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        authDAO.clear();
        authDAO.createAuth("Johny");
        authDAO.createAuth("Bill");
        authDAO.createAuth("Sam");
    }
}
