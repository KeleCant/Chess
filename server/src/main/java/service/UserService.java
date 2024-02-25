package service;

import dataAccess.*;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RegistrationRequest;
import results.LoginResult;
import results.RegistrationResult;

public class UserService {

    //Register a new user.
    public RegistrationResult register (RegistrationRequest request) throws DataAccessException {
        UserDAO UserDAO = new UserDAOMemory();
        AuthDAO AuthDAO = new AuthDAOMemory();

        //check to see if username is taken
        UserDAO.checkUsername(request.username());

        UserDAO.createUser(request.username(), request.password(), request.email());
        return new RegistrationResult(request.username(), AuthDAO.createAuth(request.username()));
    }

    //Logs in an existing user (returns a new authToken).
    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserDAO userDAO = new UserDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();

        //check to see that username matches password
        userDAO.checkPassword(request.username(), request.password());

        return new LoginResult(request.username(), authDAO.createAuth(request.username()));
    }

    //Logs out the user represented by the authToken.
    public void logout(LogoutRequest request) throws DataAccessException {
        AuthDAO authDAO = new AuthDAOMemory();
        authDAO.deleteAuth(request.authToken());
    }

}
