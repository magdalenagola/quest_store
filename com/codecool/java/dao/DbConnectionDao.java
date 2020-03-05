package codecool.java.dao;

import codecool.java.model.DatabaseConnection;

public class DbConnectionDao {
    DatabaseConnection dbconnection;

    public DbConnectionDao() {
        dbconnection = DatabaseConnection.INSTANCE;
    }
}
