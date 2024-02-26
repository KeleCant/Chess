package service;

import dataAccess.*;
import requests.*;
import results.*;

public class UserService {

    static AuthDAO authDAO;
    static UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    //Register a new user.
    public static RegistrationResult register(RegistrationRequest request) throws DataAccessException {

        //check to see if username is taken
        userDAO.checkUsername(request.username());

        userDAO.createUser(request.username(), request.password(), request.email());
        return new RegistrationResult(request.username(), authDAO.createAuth(request.username()));
    }

    //Logs in an existing user (returns a new authToken).
    public static LoginResult login(LoginRequest request) throws DataAccessException {

        //check to see that username matches password
        userDAO.checkPassword(request.username(), request.password());

        return new LoginResult(request.username(), authDAO.createAuth(request.username()));
    }

    //Logs out the user represented by the authToken.
    public static LogoutResult logout(LogoutRequest request) throws DataAccessException {
        authDAO.deleteAuth(request.authToken());
        return new LogoutResult();
    }

}
