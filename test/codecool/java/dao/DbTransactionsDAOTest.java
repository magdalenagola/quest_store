package codecool.java.dao;

import codecool.java.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.Date;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbTransactionsDAOTest {
    DbTransactionsDAO transactionsDAO = new DbTransactionsDAO();
    DbQuestDAO dbQuestDAO = new DbQuestDAO();
    DbCardDAO dbCardDAO = new DbCardDAO();

    @BeforeAll
    public static void setDbToTest() {
        DatabaseConnection.INSTANCE.setEnv("test");
    }

    @Test
    void shouldLoadAllNotApprovedQuests() throws SQLException {
        List<Transaction> actual = transactionsDAO.loadAllNotApproved();
        List<Transaction> expected = createSampleNotApprovedTransactionsList();
        assertEquals(expected.toString(), actual.toString());
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
    void shouldDisplayAllTransactionsByStudent() throws SQLException {
        Student mockStudent = mock(Student.class);
        when(mockStudent.getId()).thenReturn(23);
        Map<String,List<Transaction>> expected = createSampleCardAndQuestTransactionsHashMapForStudentId23();
        Map<String,List<Transaction>> actual = transactionsDAO.displayAllTransactionsByStudent(mockStudent);
        System.out.println(expected);
        System.out.println("actual:");
        System.out.println(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    private Map<String,List<Transaction>>  createSampleCardAndQuestTransactionsHashMapForStudentId23() throws SQLException {
        Map<String,List<Transaction>> studentTransactions = new HashMap<>();
        List<Transaction> cardsList = new ArrayList<>();
        Card card = dbCardDAO.selectCardById(6);
        Date dateBought =Date.valueOf("2020-03-05");
        Transaction cardTransaction = new CardTransaction(card, 23, dateBought, 8);
        cardsList.add(cardTransaction);
        List<Transaction> questsList = new ArrayList<>();
        studentTransactions.put("Cards", cardsList);
        Quest quest = dbQuestDAO.selectQuestById(4);
        Date dateAdded =Date.valueOf("2020-02-06");
        Transaction questTransaction = new QuestTransaction(quest, 23, dateAdded, 5);
        questsList.add(questTransaction);
        studentTransactions.put("Quests", questsList);
        return studentTransactions;
    }



    @Test
    void addCardTransaction() {
    }

    @Test
    void save() {
    }

    @Test
    void shouldLoadAllTransactions() {
        assertEquals(24, transactionsDAO.loadAll().size());
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