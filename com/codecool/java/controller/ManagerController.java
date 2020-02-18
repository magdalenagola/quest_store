package codecool.java.controller;

import codecool.java.dao.DbMentorDAO;
import codecool.java.dao.MentorDAO;
import codecool.java.model.Mentor;
import codecool.java.model.User;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

import java.sql.SQLException;
import java.util.List;

public class ManagerController {
    private MentorDAO mentorDAO;
    private Display terminalView;

    public ManagerController() throws SQLException, ClassNotFoundException {
        mentorDAO = new DbMentorDAO();
        terminalView = new TerminalView();
    }

    public void run() {
        String[] options = {"Add new mentor", "Edit mentor's profile", "Show all mentors", "Remove mentor", "Activate mentor"};
        terminalView.displayOptions(options);
        int userInput = terminalView.getOptionInput(options.length);
        switch(userInput){
            case 1:
                try {
                    addNewMentor();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    editMentor();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    showAllMentors();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    disableMentor();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    activateMentor();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private int chooseMentor() throws SQLException {
        List<User> mentors =  mentorDAO.loadAll();

        terminalView.displayUsers(mentors);
        return terminalView.getOptionInput(mentors.size()) - 1;
    }

    private void disableMentor() throws SQLException {
        List<Mentor> mentors = mentorDAO.loadAll();
        Mentor mentor = mentors.get(chooseMentor());
        mentorDAO.disable(mentor);
    }

    private void activateMentor() throws SQLException {
        List<Mentor> mentors = mentorDAO.loadAll();
        Mentor mentor = mentors.get(chooseMentor());
        mentorDAO.activate(mentor);
    }

    private void showAllMentors() throws SQLException {
        List<User> mentors = mentorDAO.loadAll();
        terminalView.displayUsers(mentors);
    }

    private void editMentor() throws SQLException {
        String[] mentorDataToEdit = {"Name", "Surname", "Email", "Password", "Primary skill"};
        List<Mentor> mentors = mentorDAO.loadAll();
        Mentor mentor = mentors.get(chooseMentor());

        terminalView.displayOptions(mentorDataToEdit);
        String[] optionsToChange = terminalView.getInputs(mentorDataToEdit);
        mentor.setName(optionsToChange[0]);
        mentor.setSurname(optionsToChange[1]);
        mentor.setLogin(optionsToChange[2]);
        mentor.setPassword(optionsToChange[3]);
        mentor.setPrimarySkill(optionsToChange[4]);

        mentorDAO.update(mentor);

        terminalView.displayMessage("Successfully edited mentor data!");
    }

    private void addNewMentor() throws SQLException {
        String[] requiredUserData = {"Name", "Surname", "Email", "Password", "Primary skill"};
        String[] newData =  terminalView.getInputs(requiredUserData);
        Mentor newMentor;
        newMentor = new Mentor(newData[0], newData[1], newData[2], newData[3], newData[4], true);
        mentorDAO.save(newMentor);
        terminalView.displayMessage("Successfully added new mentor!");
    }
}
