package codecool.java.controller;

import codecool.java.dao.DbLoginDao;
import codecool.java.dao.LoginDao;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.model.User;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginController {
    LoginDao dao;
    Scanner input;
    Display display;

    public LoginController() {
        this.input = new Scanner(System.in);
        this.display = new TerminalView();
    }

    public User authenticate() {
        display.displayMessage("Program start.");
        User user = null;
        try {
            dao = new DbLoginDao();
            display.displayMessage("Login: ");
            String login = input.next();
            display.displayMessage("Password: ");
            String password = input.next();
            user = dao.logIn(login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NotInDatabaseException e) {
            display.displayMessage("User not found in the records.");
        }
        return user;
    }
}