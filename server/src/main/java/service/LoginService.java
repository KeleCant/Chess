package service;

import dataAccess.*;
import requests.LoginRequest;
import results.LoginResult;

public class LoginService {

    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserDAO userDAO = new UserDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();

        //check to see that username matches password
        userDAO.checkPassword(request.username(), request.password());

        return new LoginResult(request.username(), authDAO.createAuth(request.username()));
    }
}
