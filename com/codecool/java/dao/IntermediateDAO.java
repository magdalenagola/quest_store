package codecool.java.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IntermediateDAO {
    ResultSet selectEntryById(int id, String tableName) throws SQLException;
    ResultSet selectAllFromTable(String tableName) throws SQLException;
    void disableTableEntryById(int id, String tableName) throws SQLException;
    void enableTableEntryById(int id, String tableName) throws SQLException;
    void disableAllTableEntries(String tableName) throws SQLException;
    void enableAllTableEntries(String tableName) throws SQLException;
} 
