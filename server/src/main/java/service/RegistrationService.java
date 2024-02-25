package service;

import dataAccess.*;
import requests.RegistrationRequest;
import results.RegistrationResult;

public class RegistrationService {

    public RegistrationResult register (RegistrationRequest request) throws DataAccessException {
        UserDAO UserDAO = new UserDAOMemory();
        AuthDAO AuthDAO = new AuthDAOMemory();

        //check to see if username is taken
        UserDAO.checkUsername(request.username());

        UserDAO.createUser(request.username(), request.password(), request.email());
        return new RegistrationResult(request.username(), AuthDAO.createAuth(request.username()));
    }
}
