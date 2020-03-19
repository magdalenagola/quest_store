package codecool.java.dao;

import codecool.java.model.DatabaseConnection;
import codecool.java.model.Quest;
import codecool.java.model.QuestTransaction;
import codecool.java.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.Date;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DbTransactionsDAOTest {
    DbTransactionsDAO transactionsDAO = new DbTransactionsDAO();
    DbQuestDAO dbQuestDAO = new DbQuestDAO();

    @BeforeAll
    public static void setDbToTest() {
        DatabaseConnection.INSTANCE.setEnv("test");
    }

    @Test
    void loadAllNotApproved() throws SQLException {
        List<Transaction> actual = transactionsDAO.loadAllNotApproved();
        List<Transaction> expected = createSampleNotApprovedTransactionsList();
        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    private List<Transaction> createSampleNotApprovedTransactionsList() throws SQLException {
        int notApprovedQuestId = 7;
        Quest quest = dbQuestDAO.selectQuestById(notApprovedQuestId);
        Date dateAdded =Date.valueOf("2020-02-19");
        Transaction questTransaction = new QuestTransaction(quest, 2, dateAdded, 30);
        List<Transaction> unapprovedQuestsList = new ArrayList<>();
        unapprovedQuestsList.add(questTransaction);
        return unapprovedQuestsList;
    }

    @Test
    void displayAllTransactionsByStudent() {
    }

    @Test
    void getCardTransactionsByStudent() {
    }

    @Test
    void getQuestTransactionsByStudent() {
    }

    @Test
    void addCardTransaction() {
    }

    @Test
    void save() {
    }

    @Test
    void loadAll() {
    }

    @Test
    void update() {
    }

    @Test
    void disable() {
    }

    @Test
    void activate() {
    }
}