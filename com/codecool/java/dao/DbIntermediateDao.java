package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;

import java.sql.*;

public class DbIntermediateDao implements IntermediateDAO {
    BasicConnectionPool pool;

    public DbIntermediateDao() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");

    }

    @Override
    public ResultSet selectEntryById(int id, String tableName) throws SQLException {
            String orderToSql = "SELECT * FROM ? WHERE id = ?;";
            Connection c = pool.getConnection();
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setString(1, tableName);
            ps.setInt(2, id);
            return ps.executeQuery();
    }

    @Override
    public ResultSet selectAllFromTable(String tableName) throws SQLException {
        String orderToSql = ("SELECT * FROM ?");
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        return ps.executeQuery();
    }

    @Override
    public void disableTableEntryById(int id, String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = false WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.setInt(2, id);
        ps.execute();
    }

    @Override
    public void enableTableEntryById(int id, String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = true WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.setInt(2, id);
        ps.execute();
    }

    @Override
    public void disableAllTableEntries(String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = false;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.execute();
    }

    @Override
    public void enableAllTableEntries(String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = true;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.execute();
    }
}
