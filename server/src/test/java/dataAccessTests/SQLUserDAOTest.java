package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLUserDAO;
import dataAccess.UserDAO;
import model.UserData;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLUserDAOTest {
    UserDAO userDAO = new SQLUserDAO();
    public SQLUserDAOTest() throws DataAccessException {
    }
    @BeforeEach
    public void setup() throws TestException, DataAccessException {
        userDAO.clear();
        UserData dataJohny = userDAO.createUser("Johny", "password", "Johny@gmail");
    }

    @Test
    @Order(1)
    @DisplayName("Clear Service")
    public void clearService() throws Exception {
        userDAO.clear();
        assertDoesNotThrow(() -> userDAO.checkUsername("Johny"));
    }

    @Test
    @Order(2)
    @DisplayName("CreateUser")
    public void createUser() throws Exception {
        UserData dataJohny = userDAO.createUser("Jake", "password", "Jake@gmail");
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> userDAO.checkUsername("Jake"));
        assertEquals("Error: already taken", exp.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("CreateUser Negative")
    public void createUserNegative() throws Exception {
        UserData dataJohny = userDAO.createUser("Jake", "password", "Jake@gmail");
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> userDAO.createUser("Jake", "password", "Jake@gmail"));
        assertEquals("Error: already taken", exp.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("CheckUsername")
    public void checkUsername() throws Exception {
        assertDoesNotThrow(() -> userDAO.checkUsername("Sam"));
    }

    @Test
    @Order(5)
    @DisplayName("CheckUsername negative")
    public void checkUsernameNegative() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> userDAO.checkUsername("Johny"));
        assertEquals("Error: already taken", exp.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("CheckPassword")
    public void checkPassword() throws Exception {
        assertDoesNotThrow(() -> userDAO.checkPassword("Johny", "password"));
    }

    @Test
    @Order(7)
    @DisplayName("CheckUsername")
    public void checkPasswordNegative() throws Exception {
        Exception exp = Assertions.assertThrows(DataAccessException.class, () -> userDAO.checkPassword("Johny","coolkid69"));
        assertEquals("Error: unauthorized", exp.getMessage());
    }
}
