package codecool.java.handler;

import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbstudentDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CookieHelperTest {
    CookieHelper cookieHelper = new CookieHelper();
    @Test
    public void shouldReturnSessionIdFromCookieString(){
        String expected = "12345";
        String actual = cookieHelper.getSessionIdFromCookieString("session-id=12345");
        assertEquals(expected, actual);
    }
}