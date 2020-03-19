package codecool.java.dao;

import codecool.java.model.Student;

import java.sql.SQLException;

public interface StudentDAO extends DAO {
    int getCoins(Student student);
    void updateCoins(Student student, int coinsAmount);
    Student findStudentBySessionId(String sessionId);
}
