package codecool.java.handler;

import codecool.java.controller.StudentController;
import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class WalletHandlerTest {

    HttpResponse httpResponse = mock(HttpResponse.class);
    CookieHelper cookieHelper = mock(CookieHelper.class);
    StudentController studentController = new StudentController(new DbstudentDAO(),new DbTransactionsDAO());
    WalletHandler walletHandler = new WalletHandler(studentController, httpResponse, cookieHelper);
    String sessionId = "session-id-for-test";

    @Test
    void shouldGetStudentCoins() {
        int expected = 24;
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.getSessionId(httpExchange)).thenReturn(sessionId);
        int actual = studentController.getStudentCoins(walletHandler.getStudent(httpExchange));
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvokeGet() throws IOException {
        WalletHandler walletHandlerSpy = spy(new WalletHandler(studentController,httpResponse, cookieHelper));
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.getSessionId(httpExchange)).thenReturn(sessionId);
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        walletHandlerSpy.handle(httpExchange);
        verify(walletHandlerSpy).handleGET(httpExchange);
    }

    @Test
    void shouldSendResponse200() throws IOException {
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.getSessionId(httpExchange)).thenReturn(sessionId);
        String expected = String.valueOf(studentController.getStudentCoins(walletHandler.getStudent(httpExchange)));
        walletHandler.handleGET(httpExchange);
        verify(httpResponse).sendResponse200(httpExchange, expected);
    }
}