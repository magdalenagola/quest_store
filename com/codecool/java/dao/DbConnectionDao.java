package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;

import java.sql.SQLException;

public class DbConnectionDao {
    BasicConnectionPool pool;

    public DbConnectionDao() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }
}
