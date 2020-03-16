package handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.handler.CardHandler;
import codecool.java.model.Card;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CardHandlerTest {

    static CardHandler cardHandler = new CardHandler(mock(DbstudentDAO.class),mock(DbCardDAO.class));
    @Test
    public void shouldReturnFalseWhenNotEnoughCoins() {
        Card mockCard = mock(Card.class);
        int studentCoins = 5;
        when(mockCard.getCost()).thenReturn(10);
        boolean actual = cardHandler.checkCardAffordability(studentCoins,mockCard);
        assertFalse(actual);
    }
    @Test
    public void shouldReturnTrueWhenEnoughCoins() {
        Card mockCard = mock(Card.class);
        int studentCoins = 10;
        when(mockCard.getCost()).thenReturn(10);
        boolean actual = cardHandler.checkCardAffordability(studentCoins,mockCard);
        assertTrue(actual);
    }
    @Test
    public void shouldReturnJsonOfCards() {
        // Arrange
        DbCardDAO mockCardDAO = mock(DbCardDAO.class);
        CardHandler cardHandler = new CardHandler(mock(DbstudentDAO.class),mockCardDAO);
        Card Card1 = new Card(1,10,"xd","name",true,10,"title");
        Card Card2 = new Card(2,15,"dx","name2",true,15,"title2");
        when(mockCardDAO.loadAll()).thenReturn(Arrays.asList(Card1,Card2));
        // Act
        String expected =
                "[{\"quantity\":10,\"id\":1,\"title\":\"title\",\"description\":\"xd\",\"image\":\"name\",\"isActive\":true,\"cost\":10}," +
                "{\"quantity\":15,\"id\":2,\"title\":\"title2\",\"description\":\"dx\",\"image\":\"name2\",\"isActive\":true,\"cost\":15}]";
        String actual = cardHandler.getCards();
        // Assert
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
        String cookieStr = new String("sessionId=P1Cu9n8");
        String expected = "P1Cu9n8";
        String actual = cardHandler.getSessionIdFromCookieString(cookieStr);
        assertEquals(expected, actual);
    }
}