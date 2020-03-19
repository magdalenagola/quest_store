package codecool.java.handler;

import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.model.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class TransactionsHandlerTest {
    DbTransactionsDAO mockTransactionsDAO;
    TransactionsHandler transactionsHandler;

    @BeforeEach
    void setUp(){
        mockTransactionsDAO = mock(DbTransactionsDAO.class);
        transactionsHandler = new TransactionsHandler(mock(DbstudentDAO.class),mockTransactionsDAO);
    }

    @Test
    public void shouldReturnTransactionsForGivenStudent(){
        Student dummyStudent = mock(Student.class);
        when(mockTransactionsDAO.displayAllTransactionsByStudent(dummyStudent)).thenReturn(createSampleMapOfStudentQuests());
        String expected = "{\"Quests\":[{\"item\":{\"category\":\"category\",\"id\":1,\"title\":\"title\",\"description\":\"description\",\"image\":\"image\",\"isActive\":true,\"cost\":22},\"userId\":1,\"date\":" +
                createSampleGsonDate() +
                ",\"cost\":22}]}";
        String actual = transactionsHandler.getStudentTransactions(dummyStudent);
        assertEquals(expected,actual);
    }

    private Map<String, List<Transaction>> createSampleMapOfStudentQuests(){
        Map<String, List<Transaction>> quests = new HashMap<>();
        Item quest = new Quest(1, "title", "description", "image", true, 22, "category");
        Transaction questTransaction = new QuestTransaction(quest, 1, new Date(2222), 22);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(questTransaction);
        quests.put("Quests",transactions);
        return quests;
    }

    private String createSampleGsonDate(){
        Date date = new Date(2222);
        Gson gson = new Gson();
        return gson.toJson(date);
    }
}