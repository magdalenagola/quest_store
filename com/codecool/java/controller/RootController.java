package codecool.java.controller;

import codecool.java.dao.NotInDatabaseException;
import codecool.java.model.User;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

public class RootController {
    public void run() {
        LoginController loginController = new LoginController();
        Display display = new TerminalView();
        try {
            User user = loginController.authenticate();
            user.start();
        }catch(NotInDatabaseException e){
            display.displayMessage(e.getMessage());
            run();
        }
    }
}
