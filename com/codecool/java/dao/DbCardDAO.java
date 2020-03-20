package codecool.java.dao;

import codecool.java.model.Card;
import codecool.java.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCardDAO extends DbConnectionDao implements CardDAO {

    public DbCardDAO(){
        super();
    }

    @Override
    public Card selectCardById(int id){
        Card card = null;
        try{
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM cards WHERE id = ?;");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
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
        }catch(SQLException e){
            e.printStackTrace();
        }
        return card;
    }

    private ResultSet selectEntryById(int id){
        ResultSet rs = null;
        try {
            String orderToSql = "SELECT * FROM cards WHERE id = ?;";
            Connection c = dbconnection.getConnection();
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setInt(1, id);
            rs =  ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public void enableAllCards(){
        try {
            String orderToSql = "UPDATE cards SET is_active = true;";
            Connection c = dbconnection.getConnection();
            c.createStatement().execute(orderToSql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void disableAllCards(){
        try {
            String orderToSql = "UPDATE cards SET is_active = false;";
            Connection c = dbconnection.getConnection();
            c.createStatement().execute(orderToSql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Object o) {
        Card card = (Card) o;
        String orderToSql = "INSERT INTO cards (title, description, image, quantity, is_active, cost) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            Connection c = dbconnection.getConnection();
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setString(1, card.getTitle());
            ps.setString(2, card.getDescription());
            ps.setString(3, card.getImage());
            ps.setInt(4, card.getQuantity());
            ps.setBoolean(5, card.isActive());
            ps.setFloat(6, card.getCost());
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List loadAll() {
        List<Object> cards = new ArrayList<>();
        Card card;
        try {
            ResultSet rs = selectAllFromTable();
            while (rs.next()) {
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
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cards;
    }

    private ResultSet selectAllFromTable(){
        ResultSet rs = null;
        try {
            String orderToSql = ("SELECT * FROM cards");
            Connection c = dbconnection.getConnection();
            rs = c.createStatement().executeQuery(orderToSql);
        }catch(SQLException e){
            e.printStackTrace();
            }
        return rs;
    }

    @Override
    public void update(Object o){
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET title = ?, description = ?, image = ?, quantity = ?, is_active = ?, cost = ? WHERE id = ?;";
        try {
            Connection c = dbconnection.getConnection();
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setString(1, card.getTitle());
            ps.setString(2, card.getDescription());
            ps.setString(3, card.getImage());
            ps.setInt(4, card.getQuantity());
            ps.setBoolean(5, card.isActive());
            ps.setFloat(6, card.getCost());
            ps.setInt(7, card.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void activate(Object o){
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET is_active = true WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setInt(1, card.getId());
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void disable(Object o){
        Card card = (Card) o;
        String orderToSql = "UPDATE cards SET is_active = false WHERE id = ?;";
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(orderToSql);
            ps.setInt(1, card.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Card> getCardsByStudent(Student student) {
        Connection c = dbconnection.getConnection();
        List<Card> cardsList = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM student_cards JOIN cards on (cards.id = student_cards.card_id) WHERE student_cards.user_id = ?;");
            ps.setInt(1, student.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("card_id");
                Integer coinsPaid = rs.getInt("cost");
                Date dateBought = rs.getDate("date_bought");
                Integer userId = rs.getInt("user_id");
                Card card = new Card(rs.getInt("cost"), rs.getString("description"), rs.getString("image"), true, rs.getInt("quantity"), rs.getString("title"));
                cardsList.add(card);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cardsList;
    }

}