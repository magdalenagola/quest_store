package codecool.java.handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.model.Card;
import org.junit.jupiter.api.Test;
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
        
        // Act
        
        // Assert
    } 
}