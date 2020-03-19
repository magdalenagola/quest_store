package codecool.java.handler;

import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.model.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class TransactionsHandlerTest {
    @Disabled
    @Test
    public void shouldReturnTransactionsForGivenStudent(){
        DbTransactionsDAO mockTransactionsDAO = mock(DbTransactionsDAO.class);
        TransactionsHandler transactionsHandler = new TransactionsHandler(mock(DbstudentDAO.class),mockTransactionsDAO);
        Student dummyStudent = mock(Student.class);
        Map<String, List<Transaction>> quests = new HashMap<>();
        Item quest = new Quest(1, "title", "description", "image", true, 22, "category");
        Transaction questTransaction = new QuestTransaction(quest, 1, new Date(2222), 22);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(questTransaction);
        quests.put("Quests",transactions);
        when(mockTransactionsDAO.displayAllTransactionsByStudent(dummyStudent)).thenReturn(quests);
        String expected = "{\"Quests\":[{\"item\":{\"category\":\"category\",\"id\":1,\"title\":\"title\",\"description\":\"description\",\"image\":\"image\",\"isActive\":true,\"cost\":22},\"userId\":1,\"date\":\"sty 1, 1970\",\"cost\":22}]}";
        String actual = transactionsHandler.getStudentTransactions(dummyStudent);
        assertEquals(expected,actual);

    }
}