package codecool.java.dao;

import java.sql.SQLException;

public interface CardDAO extends DAO {
    Object selectCardById(int id);
    void enableAllCards();
    void disableAllCards();
}