package codecool.java.controller;
package codecool.java.view;

import codecool.java.dao.DbMentorDAO;
import codecool.java.model.Mentor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerController {
    DbMentorDAO dbMentorDAO;
    TerminalView terminalView;
    public void run() {
        String[] options = {"Add new mentor", "Edit mentor's profile", "Show all mentors"};
        View.displayOptions(options);
        int userInput = View.getOptionInput(options.length);
        switch(userInput){
            case 1:
                addNewMentor(mentors);
                break;
            case 2:
                editMentor(mentor);
                break;
            case 3:
                showAllMentors();
                break;
        }
    }

    private void showAllMentors() throws SQLException, ClassNotFoundException {
        List<Mentor> mentors= new ArrayList<>();
        terminalView = new TerminalView();
        dbMentorDAO = new DbMentorDAO();
        mentors = dbMentorDAO.loadAll();
        terminalView.displayUsers(mentors);
    }

    private void editMentor(Mentor mentor) {
    }

    private Mentor addNewMentor() {
    }
}
