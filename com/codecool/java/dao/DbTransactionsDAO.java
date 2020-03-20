package codecool.java.dao;

import codecool.java.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbTransactionsDAO extends DbConnectionDao implements TransactionsDAO{

    public DbTransactionsDAO(){
        super();
    }

    @Override
    public List<Transaction> loadAllNotApproved(){
        Connection c = dbconnection.getConnection();
        List<Transaction> unapprovedQuestsList = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM student_quests WHERE date_approved IS NULL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer coinsReceived = rs.getInt("cost");
                Date dateAdded = rs.getDate("date_added");
                Integer questId = rs.getInt("quest_id");
                Integer userId = rs.getInt("user_id");
                DbQuestDAO dbQuestDAO = new DbQuestDAO();
                Quest quest = dbQuestDAO.selectQuestById(questId);
                Transaction questTransaction = new QuestTransaction(quest, userId, dateAdded, coinsReceived);
                unapprovedQuestsList.add(questTransaction);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return unapprovedQuestsList;
    }

    @Override
    public Map<String,List<Transaction>> displayAllTransactionsByStudent(Student student){
        Map<String,List<Transaction>> studentTransactions = new HashMap<>();
        try {
            studentTransactions.put("Cards", getCardTransactionsByStudent(student));
            studentTransactions.put("Quests", getQuestTransactionsByStudent(student));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return studentTransactions;
    }


    public List<Transaction> getCardTransactionsByStudent(Student student) throws SQLException {
        Connection c = dbconnection.getConnection();
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM student_cards JOIN cards on (cards.id = student_cards.card_id) WHERE student_cards.user_id = ?;");
        ps.setInt(1, student.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Integer id = rs.getInt("card_id");
            Integer coinsPaid = rs.getInt("cost");
            Date dateBought = rs.getDate("date_bought");
            Integer userId = rs.getInt("user_id");
            Card card = new Card(id, rs.getInt("cost"), rs.getString("description"), rs.getString("image"), true, rs.getInt("quantity"), rs.getString("title"));
            Transaction cardTransaction = new CardTransaction(card, userId, dateBought, coinsPaid);
            transactionsList.add(cardTransaction);
        }
        return transactionsList;
    }
    public List<Transaction> getQuestTransactionsByStudent(Student student) throws SQLException {
        Connection c = dbconnection.getConnection();
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM student_quests JOIN quests on (quests.id = student_quests.quest_id) WHERE user_id = ?");
        ps.setInt(1, student.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Integer coinsReceived = rs.getInt("cost");
            Integer questId = rs.getInt("quest_id");
            Date dateAdded = rs.getDate("date_added");
            Date dateApproved = rs.getDate("date_approved");
            Integer userId = rs.getInt("user_id");
            Quest quest = new Quest(questId, rs.getString("title"), rs.getString("description"), rs.getString("image"), rs.getBoolean("is_active"), rs.getInt("cost"), rs.getString("category"));
            Transaction questTransaction = new QuestTransaction(quest, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
        }
        return transactionsList;
    }
        public void addCardTransaction(CardTransaction cardTransaction){
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO student_cards(card_id, cost, date_bought, user_id) VALUES(?, ?, ?, ?)");
            ps.setInt(1, cardTransaction.getItemId());
            ps.setInt(2, cardTransaction.getCost());
            ps.setDate(3, cardTransaction.getDate());
            ps.setInt(4, cardTransaction.getUserId());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void addQuestTransaction(QuestTransaction questTransaction){
        Connection c = dbconnection.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO student_quests(cost, date_added, date_approved, user_id, quest_id) VALUES(?, ?, ?, ?, ?)");
            ps.setInt(1, questTransaction.getCost());
            ps.setDate(2, questTransaction.getDate());
            if (questTransaction.getApprovalDate() != null){
                ps.setDate(3, questTransaction.getApprovalDate());
            }else{
                ps.setNull(3, java.sql.Types.DATE);
            }
            ps.setInt(4, questTransaction.getUserId());
            ps.setInt(5, questTransaction.getItemId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    private void updateStudentQuest(QuestTransaction questTransaction) throws SQLException {
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE student_quests SET cost = ?," +
                "date_added = ?, date_approved = ?, user_id = ? WHERE quest_id = ?");
        ps.setInt(1, questTransaction.getCost());
        ps.setDate(2, questTransaction.getDate());
        ps.setDate(3, questTransaction.getApprovalDate());
        ps.setInt(4, questTransaction.getUserId());
        ps.setInt(5, questTransaction.getItemId());
        ps.executeUpdate();
    }

    private void updateStudentCard(CardTransaction cardTransaction) throws SQLException {
        Connection c = dbconnection.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE student_quests SET cost = ?," +
                "date_added = ?, user_id = ? WHERE quest_id = ?");
        ps.setInt(1, cardTransaction.getCost());
        ps.setDate(2, cardTransaction.getDate());
        ps.setInt(3, cardTransaction.getUserId());
        ps.setInt(4, cardTransaction.getItemId());
        ps.executeUpdate();
    }

    @Override
    public void save(Object object){
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            addCardTransaction(cardTransaction);
        } else if (object instanceof QuestTransaction){
            QuestTransaction questTransaction = (QuestTransaction) object;
            addQuestTransaction(questTransaction);
        }
    }

    @Override
    public List loadAll(){
        Connection c = dbconnection.getConnection();
        List<Transaction> transactionsList = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM student_cards");
            ResultSet rs = ps.executeQuery();
            DbCardDAO cardDAO = new DbCardDAO();
            while(rs.next()) {
                Integer id = rs.getInt("card_id");
                Integer coinsPaid = rs.getInt("cost");
                Date dateBought = rs.getDate("date_bought");
                Integer userId = rs.getInt("user_id");
                Card card = cardDAO.selectCardById(id);
                Transaction cardTransaction = new CardTransaction(card, userId, dateBought, coinsPaid);
                transactionsList.add(cardTransaction);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        DbQuestDAO dbQuestDAO = new DbQuestDAO();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM student_quests");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer coinsReceived = rs.getInt("cost");
                Date dateAdded = rs.getDate("date_added");
                Date dateApproved = rs.getDate("date_approved");
                Integer questId = rs.getInt("quest_id");
                Integer userId = rs.getInt("user_id");
                Quest quest = dbQuestDAO.selectQuestById(questId);
                Transaction questTransaction = new QuestTransaction(quest, userId, dateAdded, coinsReceived);
                transactionsList.add(questTransaction);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return transactionsList;
    }

    @Override
    public void update(Object object) throws SQLException {
    if (object instanceof CardTransaction){
        updateStudentCard((CardTransaction) object);

    } else if (object instanceof QuestTransaction) {
        updateStudentQuest((QuestTransaction) object);

        }
     }

    @Override
    public void disable(Object object) throws SQLException {
        Connection c = dbconnection.getConnection();
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_cards SET is_active = false WHERE id = %d;", cardTransaction.getItemId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction) {
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_quests SET is_active = false WHERE id = %d;", questTransaction.getItemId()));
            ps.executeUpdate();
        }
    }

    @Override
    public void activate(Object object) throws SQLException {
        Connection c = dbconnection.getConnection();
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_cards SET is_active = true WHERE id = %d;", cardTransaction.getItemId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction) {
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("UPDATE student_quests SET is_active = true WHERE id = %d;", questTransaction.getItemId()));
            ps.executeUpdate();
        }
    }

    public void remove(Object object) throws SQLException {
        Connection c = dbconnection.getConnection();
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("DELETE FROM student_cards WHERE card_id = %d;", cardTransaction.getItemId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction) {
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = c.prepareStatement(String.format("DELETE FROM student_quests WHERE quest_id = %d;", questTransaction.getItemId()));
            ps.executeUpdate();
        }
    }
}
