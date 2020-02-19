package codecool.java.dao;

import codecool.java.model.Card;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbCardDAO extends DbConnectionDao implements CardDAO {

    public DbCardDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Card selectCardById(int id) throws SQLException {
        ResultSet rs = selectEntryById(id);
        Card card = null;
        while(rs.next()){
            String title = rs.getString("title");
            String description = rs.getString("description");
            String image = rs.getString("image");
            int quantity = rs.getInt("quantity");
            boolean isActive = rs.getBoolean("is_active");
            int cost = rs.getInt("cost");
            card = new Card(
                    id,
                    cost,
                    description,
                    image,
                    isActive,
                    quantity,
                    title
            );
        }
        return card;
    }

    private ResultSet selectEntryById(int id) throws SQLException {
        String orderToSql = "SELECT * FROM cards WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, id);
        return ps.executeQuery();
    }

    @Override
    public void enableAllCards() throws SQLException {
        String orderToSql = "UPDATE cards SET is_active = true;";
        Connection c = dbconnection.getConnection();
        c.createStatement().execute(orderToSql);
    }

    @Override
    public void disableAllCards() throws SQLException {
        String orderToSql = "UPDATE cards SET is_active = false;";
        Connection c = dbconnection.getConnection();
        c.createStatement().execute(orderToSql);
    }

    @Override
    public void save(Object o) throws SQLException {
        Card card = (Card) o;
        String orderToSql = "INSERT INTO cards (title, description, image, quantity, is_active, cost) VALUES (?, ?, ?, ?, ?, ?);";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, card.getTitle());
        ps.setString(2, card.getDescription());
        ps.setString(3, card.getImageName());
        ps.setInt(4, card.getQuantity());
        ps.setBoolean(5, card.isActive());
        ps.setFloat(6, card.getCost());
        ps.execute();
    }

    @Override
    public List loadAll() throws SQLException {
        List<Object> cards = new ArrayList<>();
        Card card;
        ResultSet rs = selectAllFromTable();
        while(rs.next()){
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String image = rs.getString("image");
            int quantity = rs.getInt("quantity");
            boolean isActive = rs.getBoolean("is_active");
            int cost = rs.getInt("cost");
            card = new Card(
                    id,
                    cost,
                    description,
                    image,
                    isActive,
                    quantity,
                    title
            );
            cards.add(card);
        }
        return cards;
    }

    private ResultSet selectAllFromTable() throws SQLException {
        String orderToSql = ("SELECT * FROM cards");
        Connection c = dbconnection.getConnection();
        return c.createStatement().executeQuery(orderToSql);
    }

    @Override
    public void update(Object o) throws SQLException {
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET title = ?, description = ?, image = ?, quantity = ?, is_active = ?, cost = ? WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, card.getTitle());
        ps.setString(2, card.getDescription());
        ps.setString(3, card.getImageName());
        ps.setInt(4, card.getQuantity());
        ps.setBoolean(5, card.isActive());
        ps.setFloat(6, card.getCost());
        ps.setInt(7, card.getId());
        ps.execute();
    }

    @Override
    public void disable(Object o) throws SQLException {
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET is_active = true WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, card.getId());
        ps.execute();
    }

    @Override
    public void activate(Object o) throws SQLException {
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET is_active = false WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setInt(1, card.getId());
        ps.execute();
    }
}