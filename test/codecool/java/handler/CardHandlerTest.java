package handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.handler.CardHandler;
import codecool.java.model.Card;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CardHandlerTest {

    static CardHandler cardHandler = new CardHandler(mock(DbstudentDAO.class),mock(DbCardDAO.class));

    @Test
    public void shouldReturnFalseWhenNotEnoughCoins() {
        int studentCoins = 5;
        boolean actual = cardHandler.checkCardAffordability(studentCoins,10);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrueWhenEnoughCoins() {
        int studentCoins = 10;
        boolean actual = cardHandler.checkCardAffordability(studentCoins,10);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnJsonOfCards() {
        DbCardDAO mockCardDAO = mock(DbCardDAO.class);
        CardHandler cardHandler = new CardHandler(mock(DbstudentDAO.class),mockCardDAO);
        Card Card1 = new Card(1,10,"xd","name",true,10,"title");
        Card Card2 = new Card(2,15,"dx","name2",true,15,"title2");
        when(mockCardDAO.loadAll()).thenReturn(Arrays.asList(Card1,Card2));
        String expected =
                "[{\"quantity\":10,\"id\":1,\"title\":\"title\",\"description\":\"xd\",\"image\":\"name\",\"isActive\":true,\"cost\":10}," +
                "{\"quantity\":15,\"id\":2,\"title\":\"title2\",\"description\":\"dx\",\"image\":\"name2\",\"isActive\":true,\"cost\":15}]";
        String actual = cardHandler.getCards();
        assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnCardIdFromUri() throws URISyntaxException {
        URI uri = new URI("/cards/buy/3");
        int expected = 3;
        int actual = cardHandler.getCardIDFromURI(uri);
        assertEquals(expected, actual);
    }
    @Test
    public void shouldReturnSessionIdFromCookieString(){
        String expected = "P1Cu9n8";
        String actual = cardHandler.getSessionIdFromCookieString("sessionId=P1Cu9n8");
        assertEquals(expected, actual);
    }
}