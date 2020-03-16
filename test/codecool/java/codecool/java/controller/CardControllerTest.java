package codecool.java.controller;

import codecool.java.dao.CardDAO;
import codecool.java.dao.DbCardDAO;
import codecool.java.model.Card;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CardControllerTest {

    CardController cardController = new CardController(mock(CardDAO.class));

    @Test
    public void shouldReturnJsonOfCards() {
        DbCardDAO mockCardDAO = mock(DbCardDAO.class);
        CardController cardController = new CardController(mockCardDAO);
        Card Card1 = new Card(1,10,"xd","name",true,10,"title");
        Card Card2 = new Card(2,15,"dx","name2",true,15,"title2");
        when(mockCardDAO.loadAll()).thenReturn(Arrays.asList(Card1,Card2));
        String expected =
                "[{\"quantity\":10,\"id\":1,\"title\":\"title\",\"description\":\"xd\",\"image\":\"name\",\"isActive\":true,\"cost\":10}," +
                        "{\"quantity\":15,\"id\":2,\"title\":\"title2\",\"description\":\"dx\",\"image\":\"name2\",\"isActive\":true,\"cost\":15}]";
        String actual = cardController.getCardsJsonList();
        assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnCardIdFromUri() throws URISyntaxException {
        URI uri = new URI("/cards/buy/3");
        int expected = 3;
        int actual = cardController.getCardIDFromURI(uri);
        assertEquals(expected, actual);
    }
}