package codecool.java.dao;

import codecool.java.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbTransactionsDAO extends DbConnectionDao implements TransactionsDAO{

    public DbTransactionsDAO() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public List<Transaction> loadAllNotApproved() throws SQLException {
        List<Transaction> unapprovedQuestsList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM student_quests WHERE date_approved IS NULL");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("cost");
            Date dateAdded = rs.getDate("date_added");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            DbQuestDAO dbQuestDAO = null;
            try {
                dbQuestDAO = new DbQuestDAO();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Quest quest = dbQuestDAO.selectQuestById(questId);
            Transaction questTransaction = new QuestTransaction(quest, userId, dateAdded, coinsReceived);
            unapprovedQuestsList.add(questTransaction);
        }
        return unapprovedQuestsList;
    }

    @Override
    public Map<String,List<Transaction>> displayAllTransactionsByStudent(Student student) throws SQLException {
        Map<String,List<Transaction>> studentTransactions = new HashMap<>();
        studentTransactions.put("Cards",getCardTransactionsByStudent(student));
        studentTransactions.put("Quests",getQuestTransactionsByStudent(student));
        return studentTransactions;
    }


    public List<Transaction> getCardTransactionsByStudent(Student student) throws SQLException {
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM student_cards JOIN cards on (cards.id = student_cards.card_id) WHERE student_cards.user_id = ?;");
        ps.setInt(1, student.getId());
        ResultSet rs = ps.executeQuery();
        DbCardDAO cardDAO = null;
        try {
            cardDAO = new DbCardDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            Integer id = rs.getInt("card_id");
            Integer coinsPaid = rs.getInt("cost");
            Date dateBought = rs.getDate("date_bought");
            Integer userId = rs.getInt("user_id");
            Card card = new Card(rs.getInt("cost"), rs.getString("description"), rs.getString("image"), true, rs.getInt("quantity"), rs.getString("title"));
            Transaction cardTransaction = new CardTransaction(card, userId, dateBought, coinsPaid);
            transactionsList.add(cardTransaction);
        }
        return transactionsList;
    }
    public List<Transaction> getQuestTransactionsByStudent(Student student) throws SQLException {
        
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM student_quests JOIN quests on (quests.id = student_quests.quest_id) WHERE user_id = ?");
        ps.setInt(1, student.getId());
        ResultSet rs = ps.executeQuery();
        DbQuestDAO dbQuestDAO = null;
        try {
            dbQuestDAO = new DbQuestDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            Integer coinsReceived = rs.getInt("cost");
            Integer questId = rs.getInt("quest_id");
            Date dateAdded = rs.getDate("date_added");
            Date dateApproved = rs.getDate("date_approved");
            Integer userId = rs.getInt("user_id");
            Quest quest = new Quest(questId, rs.getString("title"), rs.getString("description"), rs.getString("image"), rs.getBoolean("is_active"), rs.getInt("cost"), rs.getString("category"));
            Transaction questTransaction = new QuestTransaction(quest, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
            return transactionsList;
        }
        
        return transactionsList;
    }
        public void addCardTransaction(CardTransaction cardTransaction) throws SQLException {
        
        PreparedStatement ps = conn.prepareStatement("INSERT INTO student_cards(card_id, cost, date_bought, user_id) VALUES(?, ?, ?, ?)");
        ps.setInt(1, cardTransaction.getItemId());
        ps.setInt(2, cardTransaction.getCost());
        ps.setDate(3, cardTransaction.getDate());
        ps.setInt(4, cardTransaction.getUserId());
        ps.executeUpdate();
        
    }

    private void addQuestTransaction(QuestTransaction questTransaction) throws SQLException {
        
        PreparedStatement ps = conn.prepareStatement("INSERT INTO student_quests(cost, date_added, date_approved, user_id, quest_id) VALUES(?, ?, ?, ?, ?)");
        ps.setInt(1, questTransaction.getCost());
        ps.setDate(2, questTransaction.getDate());
        ps.setNull(3, java.sql.Types.DATE);
        ps.setInt(4, questTransaction.getUserId());
        ps.setInt(5, questTransaction.getItemId());
        ps.executeUpdate();
        
    }


    private void updateStudentQuest(QuestTransaction questTransaction) throws SQLException {
        
        PreparedStatement ps = conn.prepareStatement("UPDATE student_quests SET cost = ?," +
                "date_added = ?, date_approved = ?, user_id = ? WHERE quest_id = ?");
        ps.setInt(1, questTransaction.getCost());
        ps.setDate(2, questTransaction.getDate());
        ps.setDate(3, questTransaction.getApprovalDate());
        ps.setInt(4, questTransaction.getUserId());
        ps.setInt(5, questTransaction.getItemId());
        ps.executeUpdate();
        
    }

    private void updateStudentCard(CardTransaction cardTransaction) throws SQLException {
        
        PreparedStatement ps = conn.prepareStatement("UPDATE student_quests SET cost = ?," +
                "date_added = ?, user_id = ? WHERE quest_id = ?");
        ps.setInt(1, cardTransaction.getCost());
        ps.setDate(2, cardTransaction.getDate());
        ps.setInt(3, cardTransaction.getUserId());
        ps.setInt(4, cardTransaction.getItemId());
        ps.executeUpdate();
        
    }

    @Override
    public void save(Object object) throws SQLException {
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            addCardTransaction(cardTransaction);
        } else if (object instanceof QuestTransaction){
            QuestTransaction questTransaction = (QuestTransaction) object;
            addQuestTransaction(questTransaction);
        }
    }

    @Override
    public List loadAll() throws SQLException {
        
        List<Transaction> transactionsList = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM student_cards");
        ResultSet rs = ps.executeQuery();
        DbCardDAO cardDAO = null;
        try {
            cardDAO = new DbCardDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(rs.next()) {
            Integer id = rs.getInt("card_id");
            Integer coinsPaid = rs.getInt("cost");
            Date dateBought = rs.getDate("date_bought");
            Integer userId = rs.getInt("user_id");
            Card card = cardDAO.selectCardById(id);
            Transaction cardTransaction = new CardTransaction(card, userId, dateBought, coinsPaid);
            transactionsList.add(cardTransaction);
        }
        ps = conn.prepareStatement("SELECT * FROM student_quests");
        rs = ps.executeQuery();
        DbQuestDAO dbQuestDAO = null;
        try {
            dbQuestDAO = new DbQuestDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while(rs.next()) {
            Integer coinsReceived = rs.getInt("cost");
            Date dateAdded = rs.getDate("date_added");
            Date dateApproved = rs.getDate("date_approved");
            Integer questId = rs.getInt("quest_id");
            Integer userId = rs.getInt("user_id");
            Quest quest = dbQuestDAO.selectQuestById(questId);
            Transaction questTransaction = new QuestTransaction(quest, userId, dateAdded, coinsReceived);
            transactionsList.add(questTransaction);
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
        
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = conn.prepareStatement(String.format("UPDATE student_cards SET is_active = false WHERE id = %d;", cardTransaction.getItemId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction) {
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = conn.prepareStatement(String.format("UPDATE student_quests SET is_active = false WHERE id = %d;", questTransaction.getItemId()));
            ps.executeUpdate();
        }
        
    }

    @Override
    public void activate(Object object) throws SQLException {
        
        if (object instanceof CardTransaction){
            CardTransaction cardTransaction = (CardTransaction) object;
            PreparedStatement ps = conn.prepareStatement(String.format("UPDATE student_cards SET is_active = true WHERE id = %d;", cardTransaction.getItemId()));
            ps.executeUpdate();

        } else if (object instanceof QuestTransaction) {
            QuestTransaction questTransaction = (QuestTransaction) object;
            PreparedStatement ps = conn.prepareStatement(String.format("UPDATE student_quests SET is_active = true WHERE id = %d;", questTransaction.getItemId()));
            ps.executeUpdate();
        }
        
    }
}
