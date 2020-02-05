package codecool.java.controller;

import codecool.java.model.Mentor;
import codecool.java.view.View;

public class MentorController {
    public void run(Mentor mentor){
        String[] options = {"Add new codecooler.","Add new quest.","Add new card","Update card",
        "Rate student's assigment","Display students statistics"};
        View.displayOptions(options);
        int userInput = View.getOptionInput(options.length);
        switch(userInput){
            case 1:break;
            case 2:break;
            case 3:break;
            case 4:break;
            case 5:break;
            case 6:break;
        }
    }
}
