package codecool.java.dao;

import codecool.java.model.DatabaseConnection;

import java.sql.SQLException;

public class DbConnectionDao {
    DatabaseConnection dbconnection;

    public DbConnectionDao() throws ClassNotFoundException, SQLException {
        dbconnection = DatabaseConnection.INSTANCE;
    }
}
