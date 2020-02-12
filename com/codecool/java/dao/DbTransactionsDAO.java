package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;
import codecool.java.model.Student;

import java.sql.*;
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
    public List<Transaction> loadAllNotApproved() throws SQLException {
        Connection c = pool.getConnection();
        List<QuestTransaction> unapprovedQuestsList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM student_quests WHERE date_approved IS NULL");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("coins_received");
            Date dateAdded = rs.getDate("date_added");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            Transaction questTransaction = new QuestTransaction(questId, userId, dateAdded, coinsReceived);
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
            Transaction cardTransaction = new CardTransaction(id, userId, dateBought, coinsPaid);
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
            Transaction questTransaction = new QuestTransaction(questId, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
        }
        return transactionsList;
    }

    private void addCardTransaction(CardTransaction cardTransaction) throws SQLException {
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO student_cards(cost, date_bought, user_id) VALUES(?, ?, ?)");
        ps.setInt(1, cardTransaction.getCost());
        ps.setInt(2, cardTransaction.getTransactionDate());
        ps.setInt(3, cardTransaction.getUserId());
        ps.executeUpdate();
    }

    private void addQuestTransaction(QuestTransaction questTransaction) throws SQLException {
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO student_quests(cost, date_added, date_approved, user_id) VALUES(?, ?, ?, ?)");
        ps.setInt(1, questTransaction.getCost());
        ps.setDate(2, questTransaction.getTransactionDate());
        ps.setNull(3, java.sql.Types.DATE);
        ps.setInt(4, questTransaction.getUserId());
        ps.executeUpdate();
    }


    private void updateStudentQuest(QuestTransaction questTransaction) throws SQLException {
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE student_quests SET cost = ?," +
                "date_added = ?, date_approved = ?, user_id = ? WHERE card_id = ?");
        ps.setInt(1, questTransaction.getCost());
        ps.setDate(2, questTransaction.getTransactionDate());
        ps.setDate(3, questTransaction.getApprovalDate());
        ps.setInt(4, questTransaction.getUserId());
        ps.setInt(5, questTransaction.getId());
        ps.executeUpdate();
    }

    private void updateStudentCard(CardTransaction cardTransaction) throws SQLException {
        Connection c = pool.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE student_quests SET cost = ?," +
                "date_bought = ?, user_id = ? WHERE card_id = ?");
        ps.setInt(1, cardTransaction.getCost());
        ps.setDate(2, cardTransaction.getTransactionDate());
        ps.setInt(3, cardTransaction.getUserId());
        ps.setInt(4, cardTransaction.getId());
        ps.executeUpdate();
    }

    @Override
    public void save(Object object) {
        if (object instanceof CardTransaction){
            addCardTransaction((CardTransaction) object);
        } else if (object instanceof QuestTransaction)){
            addQuestTransaction((QuestTransaction) object);
        }
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
            Transaction cardTransaction = new CardTransaction(id, userId, dateBought, coinsPaid);
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
            Transaction questTransaction = new QuestTransaction(questId, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
        }

        return transactionsList;
    }

    @Override
    public void update(Object object) throws SQLException {
    if (object instanceof CardTransaction){
        updateStudentCard((CardTransaction) object);

    } else if (object instanceof QuestTransaction)){
        updateStudentQuest((QuestTransaction) object);

        }
     }

    @Override
    public void disable(Object object) throws SQLException {
        Connection c = pool.getConnection();
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_cards SET is_active = false WHERE id = %d;", cardTransaction.getId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction)){
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_quests SET is_active = false WHERE id = %d;", questTransaction.getId()));
            ps.executeUpdate();
        }

    }

    @Override
    public void activate(Object o) throws SQLException {
        Connection c = pool.getConnection();
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_cards SET is_active = true WHERE id = %d;", cardTransaction.getId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction)){
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_quests SET is_active = true WHERE id = %d;", questTransaction.getId()));
            ps.executeUpdate();
        }
    }
}
