package codecool.java.handler;

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
    WalletHandler walletHandler = new WalletHandler(httpResponse, cookieHelper);
    String sessionId = "session-id-for-test";

    @Test
    void shouldGetStudentCoins() {
        int expected = 24;
        int actual = walletHandler.getStudentCoins(sessionId);
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvokeGet() throws IOException {
        WalletHandler walletHandlerSpy = spy(new WalletHandler(httpResponse, cookieHelper));
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.getSessionId(httpExchange)).thenReturn(sessionId);
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        walletHandlerSpy.handle(httpExchange);
        verify(walletHandlerSpy).handleGET(httpExchange, sessionId);
    }

    @Test
    void shouldSendResponse200() throws IOException {
        HttpExchange httpExchange = mock(HttpExchange.class);
        String expected = String.valueOf(walletHandler.getStudentCoins(sessionId));
        walletHandler.handleGET(httpExchange, sessionId);
        verify(httpResponse).sendResponse200(httpExchange, expected);
    }
}