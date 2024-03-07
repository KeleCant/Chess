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
    String testAuth;

    public SQLAuthDAOTests() throws DataAccessException {
    }

    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        authDAO.clear();
        authDAO.createAuth("Johny");
        authDAO.createAuth("Bill");
        testAuth = authDAO.createAuth("Sam");
    }

    @Test
    @Order(1)
    @DisplayName("Clear Service")
    public void clearService() throws Exception {
        authDAO.clear();
        AuthDAO nauthDAO = new SQLAuthDAO();
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.deleteAuth(testAuth));
        assertEquals("Error: unauthorized", exp.getMessage());
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
        @DisplayName("createAuthNegative")
        public void createAuthNegative() throws Exception {
            Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAuth("Sam"));
            assertEquals("Error: Already Logged In", exp.getMessage());
        }

    @Test
    @Order(4)
    @DisplayName("delete")
    public void delete() throws Exception {
        authDAO.deleteAuth(testAuth);
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.deleteAuth(testAuth));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("deleteNegative")
    public void deleteNegative() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.deleteAuth("000"));
        assertEquals("Error: unauthorized", exp.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("getAuth")
    public void getAuth() throws Exception {
        assertEquals(authDAO.getAuth(testAuth), true, "Auth not detected");
    }

    @Test
    @Order(6)
    @DisplayName("getAuthNegative")
    public void getAuthNegative() throws Exception {
        assertEquals(authDAO.getAuth("000"), false, "Auth detected");
    }

    @Test
    @Order(6)
    @DisplayName("getUsername")
    public void getUsername() throws Exception {
        assertEquals(authDAO.getUsername(testAuth), "Sam", "name doesnt match");
    }

    @Test
    @Order(6)
    @DisplayName("getUsernameNegative")
    public void getUsernameNegative() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> authDAO.getUsername("000"));
        assertEquals("Error: unauthorized", exp.getMessage());
    }
}

