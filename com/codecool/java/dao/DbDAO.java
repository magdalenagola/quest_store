package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;

import java.sql.*;

public class DbDAO implements DAO {
    private BasicConnectionPool pool;

    public DbDAO() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");

    }



    @Override
    public ResultSet selectAllFromTable(String tableName) throws SQLException {
        String orderToSql = ("SELECT * FROM ?");
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
