package codecool.java.dao;

import codecool.java.model.Student;

public interface StudentDAO extends DAO {
    int getCoins(Student student);
    void updateCoins(Student student, int coinsAmount);
}
