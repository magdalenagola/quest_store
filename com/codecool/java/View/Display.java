package codecool.java.View;

import java.util.ArrayList;

public interface Display {
    void displayOptions(String[]);
    int getOptionInput(int maxOptionsNumber);
    void displayUser(User user);
    void displayUsers(ArrayList<User> users);
    void displayMessage(String msg);
    void displayErrorMessage(Exception e);
}
