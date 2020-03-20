package codecool.java.dao;

import codecool.java.model.*;
import org.junit.jupiter.api.*;

import java.sql.Date;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbTransactionsDAOTest {
    static DbTransactionsDAO transactionsDAO;
    static DbQuestDAO dbQuestDAO;
    static DbCardDAO dbCardDAO;

    @BeforeAll
    public static void setDbToTest() {
//        DatabaseConnection.INSTANCE.setEnv("test");
    }

    @BeforeAll
    public static void setUp(){
        transactionsDAO = new DbTransactionsDAO();
        dbQuestDAO = new DbQuestDAO();
        dbCardDAO = new DbCardDAO();
    }

    @AfterEach
    void afterEach(TestInfo info) throws SQLException {
        if(!info.getTags().contains("cleanUpSavedCardTransaction") && !info.getTags().contains("cleanUpSavedQuestTransaction")) {
            return;
        }else if (info.getTags().contains("cleanUpSavedCardTransaction")) {
            transactionsDAO.remove(createSampleCardTransaction());
        } else if (info.getTags().contains("cleanUpSavedQuestTransaction")){
            transactionsDAO.remove(createSampleQuestTransaction());
        }
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
        assertEquals(expected.toString(), actual.toString());
    }

    private Map<String,List<Transaction>>  createSampleCardAndQuestTransactionsHashMapForStudentId23() throws SQLException {
        Map<String,List<Transaction>> studentTransactions = new HashMap<>();
        studentTransactions.put("Cards", createSampleCardsList());
        studentTransactions.put("Quests", createSampleQuestsList());
        return studentTransactions;
    }

    private List<Transaction> createSampleCardsList(){
        List<Transaction> cardsList = new ArrayList<>();
        Card card = dbCardDAO.selectCardById(6);
        Date dateBought =Date.valueOf("2020-03-05");
        Transaction cardTransaction = new CardTransaction(card, 23, dateBought, 8);
        cardsList.add(cardTransaction);
        return cardsList;
    }

    private List<Transaction> createSampleQuestsList() throws SQLException {
        List<Transaction> questsList = new ArrayList<>();
        Quest quest = dbQuestDAO.selectQuestById(4);
        Date dateAdded =Date.valueOf("2020-02-06");
        Transaction questTransaction = new QuestTransaction(quest, 23, dateAdded, 5);
        questsList.add(questTransaction);
        return questsList;
    }


    @Test
    @Tag("cleanUpSavedCardTransaction")
    void shouldSaveCardTransactionToDatabase() {
        Transaction cardTransaction = createSampleCardTransaction();
        transactionsDAO.save(cardTransaction);
        assertTrue(checkIfTransactionInDatabase(cardTransaction));
    }

    @Test
    @Tag("cleanUpSavedQuestTransaction")
    void shouldSaveQuestTransactionToDatabase() {
        Transaction questTransaction = createSampleQuestTransaction();
        transactionsDAO.save(questTransaction);
        assertTrue(checkIfTransactionInDatabase(questTransaction));
    }

    private QuestTransaction createSampleQuestTransaction() {
        Quest quest = new Quest(6, "test", "test quest", "image", true, 44, "normal");
        Date transactionDate =Date.valueOf("2020-02-09");
        QuestTransaction questTransaction = new QuestTransaction(quest, 2, transactionDate, 44);
        questTransaction.setApprovalDate(transactionDate);
        return questTransaction;
    }


    private CardTransaction createSampleCardTransaction(){
        Card card = new Card(10,44, "sample card", "sample image", true, 88, "test card");
        Date transactionDate =Date.valueOf("2020-02-06");
        return new CardTransaction(card, 2, transactionDate, 44);
    }

    private boolean checkIfTransactionInDatabase(Transaction transaction) {
        List<Transaction> transactions = transactionsDAO.loadAll();
        for (Transaction transactionItem : transactions) {
            if (transaction.equals(transactionItem)) {
                return true;
            }
        }
        return false;
    }

    @Test
    void shouldLoadAllTransactions() {
        assertEquals(24, transactionsDAO.loadAll().size());
    }
}