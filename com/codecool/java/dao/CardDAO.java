package codecool.java.dao;

import codecool.java.model.Card;
import codecool.java.model.Student;

import java.util.List;

public interface CardDAO extends DAO {
    Object selectCardById(int id);
    List<Card> getCardsByStudent(Student student);
    void enableAllCards();
    void disableAllCards();
}