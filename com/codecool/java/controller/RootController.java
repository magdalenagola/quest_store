package codecool.java.controller;

import codecool.java.dao.NotInDatabaseException;
import codecool.java.model.User;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

import java.io.IOException;

public class RootController {

    HttpController httpController = new HttpController();

    public void run() {
        LoginController loginController = new LoginController();
        Display display = new TerminalView();
        try {
            httpController.init();
            User user = loginController.authenticate();
            user.start();
        }catch(NotInDatabaseException | IOException e){
            display.displayMessage(e.getMessage());
            run();
        }
    }
}
