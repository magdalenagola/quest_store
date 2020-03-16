package handler;

import codecool.java.controller.CardController;
import codecool.java.controller.StudentController;
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

    static CardHandler cardHandler = new CardHandler(mock(CardController.class),mock(StudentController.class));

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
}