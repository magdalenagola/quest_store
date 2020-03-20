package codecool.java.dao;


import codecool.java.model.Mentor;

import java.util.List;

public interface MentorDAO extends DAO {
    String getPrimarySkillById(int mentorId);
    Mentor selectMentorById(int id);
    void saveDetails(Mentor mentor);
    void updateDetails(Mentor mentor);
    List loadAllActive();
}
