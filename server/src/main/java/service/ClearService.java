package service;

import dataAccess.*;

public class ClearService {
//    AuthDAO authDAO;
//    GameDAO gameDAO;
//    UserDAO userDAO;
//
//    public ClearService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
//        this.authDAO = authDAO;
//        this.gameDAO = gameDAO;
//        this.userDAO = userDAO;
//    }

    public static void clear(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}