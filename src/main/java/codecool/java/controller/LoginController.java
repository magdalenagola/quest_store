package codecool.java.controller;

import codecool.java.dao.LoginDao;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.model.User;

public class LoginController {
    LoginDao loginDao;

    public LoginController(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public User authenticate(String login, String password) throws NotInDatabaseException{
        User user = loginDao.logIn(login, password);
        return user;
    }
}