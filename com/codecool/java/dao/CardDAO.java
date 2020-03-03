package codecool.java.dao;

import java.sql.SQLException;

public interface CardDAO extends DAO {
    Object selectCardById(int id) throws SQLException;
    void enableAllCards() throws SQLException;
    void disableAllCards() throws SQLException;
}