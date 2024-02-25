package service;

import dataAccess.*;

public class RegistrationService {

    public String register (String username, String password, String email) throws DataAccessException {
        UserDAO UserDAO = new UserDAOMemory();
        AuthDAO AuthDAO = new AuthDAOMemory();

        //check to see if username is taken
        UserDAO.checkUsername(username);

        UserDAO.createUser(username, password,email);
        return AuthDAO.createAuth(username);
    }

}
