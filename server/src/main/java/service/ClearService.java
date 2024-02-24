package service;

import dataAccess.*;

public class ClearService {
    public void clear {
        UserDAO userDAO = new UserDAOMemory();
        AuthDAO authDAO = new AuthDAOMemory();
        GameDAO gameDAO = new GameDAOMemory();

        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
