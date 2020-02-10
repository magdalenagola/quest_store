package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;
import codecool.java.model.Mentor;
import codecool.java.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbTransactionsDAO implements TransactionsDAO{
    private BasicConnectionPool pool;

    public DbTransactionsDAO() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }

    @Override
    public List<QuestTransaction> loadAllNotApproved() throws SQLException {
        Connection c = pool.getConnection();
        List<QuestTransaction> unapprovedQuestsList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM student_quests WHERE date_approved IS NULL");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("coins_received");
            Date dateAdded = rs.getDate("date_added");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            QuestTransaction questTransaction = new QuestTransaction(questId, userId, dateAdded, coinsReceived);
            unapprovedQuestsList.add(questTransaction);
        }
        return unapprovedQuestsList;
    }

    @Override
    public List<Transaction> displayAllTransactionsByStudent(Student student) throws SQLException {
        Connection c = pool.getConnection();
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM student_cards WHERE user_id = ?");
        ps.setInt(1, student.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer id = rs.getInt("card_id");
            Integer coinsPaid = rs.getInt("coins_paid");
            Date dateBought = rs.getDate("date_bought");
            Integer userId = rs.getInt("user_id");
            CardTransaction cardTransaction = new CardTransaction(id, userId, dateBought, coinsPaid);
            transactionsList.add(cardTransaction);
        }
        ps = c.prepareStatement("SELECT * FROM student_quests WHERE user_id = ?");
        ps.setInt(1, student.getId());
        rs = ps.executeQuery();
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("coins_received");
            Date dateAdded = rs.getDate("date_added");
            Date dateApproved = rs.getDate("date_approved");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            QuestTransaction questTransaction = new QuestTransaction(questId, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
        }
        return transactionsList;
    }

    @Override
    public void addCardTransaction(CardTransaction ct) {

    }

    @Override
    public void addQuestTransaction(QuestTransaction qt) {

    }

    @Override
    public void updateStudentQuest(QuestTransaction qt) {

    }

    @Override
    public void save(Object o) {

    }

    @Override
    public List loadAll() throws SQLException {
        Connection c = pool.getConnection();
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM student_cards");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer id = rs.getInt("card_id");
            Integer coinsPaid = rs.getInt("coins_paid");
            Date dateBought = rs.getDate("date_bought");
            Integer userId = rs.getInt("user_id");
            CardTransaction cardTransaction = new CardTransaction(id, userId, dateBought, coinsPaid);
            transactionsList.add(cardTransaction);
        }
        ps = c.prepareStatement("SELECT * FROM student_quests");
        rs = ps.executeQuery();
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("coins_received");
            Date dateAdded = rs.getDate("date_added");
            Date dateApproved = rs.getDate("date_approved");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            QuestTransaction questTransaction = new QuestTransaction(questId, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
        }

        return transactionsList;
    }

    @Override
    public void update(CardTransaction cardTransaction) throws SQLException {
        //TODO
//        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE student_cards SET email =?," +
                "password = ?,name = ?,surname = ?,usertype_id = 1,is_active = ?) ");
//        ps.setString(1, mentor.getLogin());
//        ps.setString(2, mentor.getPassword());
//        ps.setString(3, mentor.getName());
//        ps.setString(4, mentor.getSurname());
//        ps.setString(5, mentor.isActive());
//        ps.executeUpdate();
//    };

    @Override
    public void update(QuestTransaction questTransaction) {}

    @Override
    public void disable(Object o) {

    }

    @Override
    public void activate(Object o) {

    }
}
