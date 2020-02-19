package codecool.java.dao;

import codecool.java.model.Quest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbQuestDAO extends DbConnectionDao implements QuestDAO {

    public DbQuestDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Quest selectQuestById(int id) throws SQLException {
        ResultSet rs = selectEntryById(id);
        Quest quest = null;
        while(rs.next()){
            String title = rs.getString("title");
            String description = rs.getString("description");
            String image = rs.getString("image");
            int quantity = rs.getInt("quantity");
            boolean isActive = rs.getBoolean("is_active");
            int cost = rs.getInt("cost");
            String category = rs.getString("category");
            quest = new Quest(
                    id,
                    title,
                    description,
                    image,
                    quantity,
                    isActive,
                    cost,
                    category
            );
        }
        return quest;
    }

    private ResultSet selectEntryById(int id) throws SQLException {
        String orderToSql = "SELECT * FROM quests WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    @Override
    public void enableAllQuests() throws SQLException {
        String orderToSql = "UPDATE quests SET is_active = true;";
        Connection c = dbconnection.getConnection();
        c.createStatement().execute(orderToSql);
    }

    @Override
    public void disableAllQuests() throws SQLException {
        String orderToSql = "UPDATE quests SET is_active = false;";
        Connection c = dbconnection.getConnection();
        c.createStatement().execute(orderToSql);
    }

    @Override
    public void save(Object o) throws SQLException {
        Quest quest = (Quest) o;
        String orderToSql = "INSERT INTO quests (title, description, image,is_active, cost, category) VALUES (?, ?, ?, ?, ?, ?);";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, quest.getTitle());
        ps.setString(2, quest.getDescription());
        ps.setString(3, quest.getImage());
        ps.setBoolean(4, quest.isActive());
        ps.setInt(5, quest.getCost());
        ps.setString(6, quest.getCategory());
        ps.execute();
    }

    @Override
    public List loadAll() throws SQLException {
        List<Quest> quests = new ArrayList<>();
        Quest quest;
        ResultSet rs = selectAllFromTable();
        while(rs.next()){
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String image = rs.getString("image");
            int quantity = rs.getInt("quantity");
            boolean isActive = rs.getBoolean("is_active");
            int cost = rs.getInt("cost");
            String category = rs.getString("category");
            quest = new Quest(
                    id,
                    title,
                    description,
                    image,
                    quantity,
                    isActive,
                    cost,
                    category
            );
            quests.add(quest);
        }
        return quests;
    }

    private ResultSet selectAllFromTable() throws SQLException {
        String orderToSql = ("SELECT * FROM quests");
        Connection c = dbconnection.getConnection();
        ResultSet rs = c.createStatement().executeQuery(orderToSql);
        return rs;
    }

    @Override
    public void update(Object o) throws SQLException {
        Quest quest = (Quest) o;
        String orderToSql = "INSERT INTO quests (title, description, image, category, is_active, cost, category) VALUES (?, ?, ?, ?, ?, ?, ?);";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, quest.getTitle());
        ps.setString(2, quest.getDescription());
        ps.setString(3, quest.getImage());
        ps.setInt(4, quest.getQuantity());
        ps.setBoolean(5, quest.isActive());
        ps.setInt(6, quest.getCost());
        ps.setString(7, quest.getCategory());
        ps.execute();
    }

    @Override
    public void disable(Object o) throws SQLException {
        Quest quest = (Quest) o;
        String orderToSql = "UPDATE quests SET is_active = false WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, quest.getId());
        ps.execute();
    }

    @Override
    public void activate(Object o) throws SQLException {
        Quest quest = (Quest) o;
        String orderToSql = "UPDATE quests SET is_active = true WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, quest.getId());
        ps.execute();
    }
}
