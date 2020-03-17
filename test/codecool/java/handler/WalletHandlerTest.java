package codecool.java.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletHandlerTest {

    WalletHandler walletHandler = new WalletHandler();

    @Test
    void shouldGetStudentCoins() {
        String sessionId = "session-id-for-test";
        int expected = 24;
        int actual = walletHandler.getStudentCoins(sessionId);
        assertEquals(expected, actual);
    }
}