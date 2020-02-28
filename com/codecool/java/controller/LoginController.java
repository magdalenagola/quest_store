package codecool.java.controller;

import codecool.java.dao.DbAuthorizationDAO;
import codecool.java.dao.LoginDao;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.model.User;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

import java.sql.SQLException;

public class LoginController {
    LoginDao dao;
    Display display;

    public LoginController() {
        this.display = new TerminalView();
    }

    public User authenticate(String login, String password) throws NotInDatabaseException{
        display.displayMessage("Program start.");
        User user = null;
        try {
            dao = new DbAuthorizationDAO();
            user = dao.logIn(login, password);
        } catch (SQLException | ClassNotFoundException e) {
            display.displayMessage(e.getMessage());
        }
        return user;
    }
}