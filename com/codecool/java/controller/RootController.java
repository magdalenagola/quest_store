package codecool.java.controller;

import codecool.java.model.User;

public class RootController {
    public void run() {
        LoginController loginController = new LoginController();
        User user =  loginController.authenticate();
        user.start();
    }
}
