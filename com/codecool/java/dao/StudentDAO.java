package codecool.java.dao;

import codecool.java.model.Student;

import java.sql.SQLException;

public interface StudentDAO extends DAO {
    int getCoins(Student student) throws SQLException;
    void updateCoins(Student student, int coinsAmount) throws SQLException;
}
