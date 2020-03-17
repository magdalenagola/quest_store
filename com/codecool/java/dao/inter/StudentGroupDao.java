package codecool.java.dao;

import codecool.java.model.Group;
import codecool.java.model.Student;
import codecool.java.model.User;

import java.sql.SQLException;
import java.util.List;

public interface StudentGroupDao extends DAO {
    Group selectGroup(int id) throws SQLException;
    void enableAllGroups() throws SQLException;
    void disableAllGroups() throws SQLException;
    List<Student> selectGroupUsers(int id) throws SQLException;
    List<Integer> getGroupIds() throws SQLException;
} 
