package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbCardDao extends DbIntermediateDao implements CardDao {
    private BasicConnectionPool pool;

    public DbCardDao() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Object selectCardById(int id) throws SQLException {
        ResultSet rs = super.selectEntryById(id, "cards");
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
                    title,
                    description,
                    image,
                    quantity,
                    isActive,
                    cost
            );
        }
        return card;
    }

    @Override
    public void enableAllCards() throws SQLException {
        super.enableAllTableEntries("cards");
    }

    @Override
    public void disableAllCards() throws SQLException {
        super.disableAllTableEntries("cards");
    }

    @Override
    public void save(Object o) throws SQLException {
        Card card = (Card) o;
        String orderToSql = "INSERT INTO cards (title, description, image, quantity, is_active, cost) VALUES (?, ?, ?, ?, ?, ?);";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, card.getTitle());
        ps.setString(2, card.getDescription());
        ps.setString(3, card.getImage());
        ps.setInt(4, card.getQuantity());
        ps.setBoolean(5, card.isActive());
        ps.setInt(6, card.getCost());
        ps.execute();
    }

    @Override
    public List loadAll() throws SQLException {
        List<Object> cards = new ArrayList<>();
        Card card;
        ResultSet rs = super.selectAllFromTable("cards");
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
                    title,
                    description,
                    image,
                    quantity,
                    isActive,
                    cost
            );
            cards.add(card);
        }
        return cards;
    }

    @Override
    public void update(Object o) throws SQLException {
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET title = ?, description = ?, image = ?, quantity = ?, is_active = ?, cost = ? WHERE id = ?;";
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement(orderToSql);
        ps.setString(1, card.getTitle());
        ps.setString(2, card.getDescription());
        ps.setString(3, card.getImage());
        ps.setInt(4, card.getQuantity());
        ps.setBoolean(5, card.isActive());
        ps.setInt(6, card.getCost());
        ps.setInt(7, card.getId());
        ps.execute();
    }

    @Override
    public void disable(Object o) {
        Card card = (Card) o;
        super.disableTableEntryById(card.getId(), "cards");
    }

    @Override
    public void activate(Object o) {
        Card card = (Card) o;
        super.enableTableEntryById(card.getId(), "cards");
    }
}