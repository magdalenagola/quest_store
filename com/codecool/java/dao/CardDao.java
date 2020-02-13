package codecool.java.dao;

import java.sql.SQLException;

public interface CardDao extends DAO {
    Object selectCardById(int id) throws SQLException;
    void enableAllCards() throws SQLException;
    void disableAllCards() throws SQLException;

    // TODO price management, new column for items
//    void divideAllCardCostsBy(int multiplier) throws SQLException;
//    void multiplyAllCardCostsTimes(int multiplier) throws SQLException;
//    void resetAllCardCosts() throws SQLException;
}