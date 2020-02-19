package codecool.java.dao;



import codecool.java.model.DatabaseConnection;

import java.sql.*;

public class DbIntermediateDao implements IntermediateDAO {
    public DatabaseConnection dbconnection;

    public DbIntermediateDao() throws SQLException, ClassNotFoundException {
        dbconnection = new DatabaseConnection();

    }

    @Override
    public ResultSet selectEntryById(int id, String tableName) throws SQLException {
            String orderToSql = "SELECT * FROM ? WHERE id = ?;";
            Connection c = dbconnection.getConnection();
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setString(1, tableName);
            ps.setInt(2, id);
            return ps.executeQuery();
    }

    @Override
    public ResultSet selectAllFromTable(String tableName) throws SQLException {
        String orderToSql = ("SELECT * FROM ?");
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        return ps.executeQuery();
    }

    @Override
    public void disableTableEntryById(int id, String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = false WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.setInt(2, id);
        ps.execute();
    }

    @Override
    public void enableTableEntryById(int id, String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = true WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.setInt(2, id);
        ps.execute();
    }

    @Override
    public void disableAllTableEntries(String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = false;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.execute();
    }

    @Override
    public void enableAllTableEntries(String tableName) throws SQLException {
        String orderToSql = "UPDATE ? SET is_active = true;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, tableName);
        ps.execute();
    }
}
