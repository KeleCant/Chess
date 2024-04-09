package service;

import dataAccess.*;

public class ClearService {

    public static void clear(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}