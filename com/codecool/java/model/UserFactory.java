package codecool.java.model;

import codecool.java.dao.DbMentorDAO;
import codecool.java.dao.MentorDAO;

import java.sql.SQLException;

public class UserFactory {
    private MentorDAO mentorDAO;

    UserFactory() throws SQLException, ClassNotFoundException {
        mentorDAO = new DbMentorDAO();
    }

    public User createUser(int id, String email, String userPassword, String name, String surname, int userTypeID, boolean isActive) throws SQLException {
        switch(userTypeID) {
            case 1:
                return new Student(id, email, userPassword, name, surname, isActive);
            case 2:
                String primarySkill = mentorDAO.getPrimarySkillById(id);
                return new Mentor(id, email, userPassword, name, surname, primarySkill, isActive);
            case 3:
                return new Manager(id, email, userPassword, name, surname, isActive);
            default:
                return null;
        }
    };
}
