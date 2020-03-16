package codecool.java.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsHandlerTest {
    TransactionsHandler transactionsHandler = new TransactionsHandler();

    @Test
    public void shouldReturnSessionIdFromCookieString(){
        String expected = "12345";
        String actual = transactionsHandler.getSessionIdFromCookieString("session-id=12345");
        assertEquals(expected, actual);
    }

}