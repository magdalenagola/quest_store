package codecool.java.dao;

import codecool.java.model.Quest;

import java.sql.SQLException;
import java.util.List;

public interface QuestDao extends DAO {
    Object selectQuestById(int id) throws SQLException;
    void enableAllQuests() throws SQLException;
    void disableAllQuests() throws SQLException;
    // TODO like CardDao
//    void divideAllQuestCostsBy(int multiplier) throws SQLException;
//    void multiplyAllQuestCostsTimes(int multiplier) throws SQLException;
//    void resetAllQuestCosts() throws SQLException;
} 
