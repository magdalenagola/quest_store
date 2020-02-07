package codecool.java.dao;

import java.sql.SQLException;

public interface MentorDAO extends DAO {
    String getPrimarySkillById(int mentorId) throws SQLException;
}
