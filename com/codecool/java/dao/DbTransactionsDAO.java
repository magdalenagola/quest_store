package codecool.java.dao;

import codecool.java.model.BasicConnectionPool;
import codecool.java.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbTransactionsDAO implements TransactionsDAO{
    private BasicConnectionPool pool;

    public DbTransactionsDAO(){
        Class.forName("org.postgresql.Driver");
        this.pool = BasicConnectionPool.create("jdbc:postgresql://ec2-176-34-237-141.eu-west-1.compute.amazonaws.com:5432/dbc5jifafq3j1h?sslmode=require",
                "utiuhfgjckzuoq", "17954f632e3663cbadb55550dd636f4c3a645ade56c3342ee89f71fc732c9672");
    }

    @Override
    public List<Transaction> loadAllNotApproved() {
        return null;
    }

    @Override
    public List<Transaction> displayAllTransactionsByStudent(Student student) {
        return null;
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
            String dateBought = rs.getString("date_bought");
            Integer userId = rs.getInt("user_id");
            CardTransaction cardTransaction = new CardTransaction(id, coinsPaid, dateBought, userId);
            transactionsList.add(cardTransaction);
        }
        ps = c.prepareStatement("SELECT * FROM student_quests");
        rs = ps.executeQuery();
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("coins_received");
            String dateAdded = rs.getString("date_added");
            String dateApproved = rs.getString("date_approved");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            QuestTransaction questTransaction = new QuestTransaction(coinsReceived, dateAdded, dateApproved, questId, userId);
            transactionsList.add(questTransaction);
        }

        return transactionsList;
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void disable(Object o) {

    }

    @Override
    public void activate(Object o) {

    }
}
