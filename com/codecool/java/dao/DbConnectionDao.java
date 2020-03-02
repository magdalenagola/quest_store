package codecool.java.dao;

import codecool.java.model.DatabaseConnection;

import java.sql.Connection;

public abstract class DbConnectionDao {
    static final Connection conn = DatabaseConnection.getConnection();
}
