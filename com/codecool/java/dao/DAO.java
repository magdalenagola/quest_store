package codecool.java.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAO {
    ResultSet selectAllFromTable(String tableName) throws SQLException;
}
