package codecool.java.handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.model.Card;
import codecool.java.model.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryHandlerTest {

    @Test
    public void shouldReturnCardsForGivenStudent(){
        DbCardDAO mockCardDAO = mock(DbCardDAO.class);
        InventoryHandler inventoryHandler = new InventoryHandler(new DbstudentDAO(), mockCardDAO);
        Student dummyStudent = mock(Student.class);
        Card card = new Card(22, "card", "image", true, 33, "card title");
        List<Card> cardsList = new ArrayList<>();
        cardsList.add(card);
        when(mockCardDAO.getCardsByStudent(dummyStudent)).thenReturn(cardsList);
        String excepted = "[{\"quantity\":33,\"id\":0,\"title\":\"card title\",\"description\":\"card\",\"image\":\"image\",\"isActive\":true,\"cost\":22}]";
        String actual = inventoryHandler.getStudentCards(dummyStudent);
        assertEquals(excepted, actual);
    }


}