package codecool.java;

import codecool.java.controller.RootController;
import codecool.java.dao.UserDao;

import java.sql.SQLException;

public class Main{
    public static void main(String[] args) {
        RootController rootController = new RootController();
        rootController.run();
    }
}