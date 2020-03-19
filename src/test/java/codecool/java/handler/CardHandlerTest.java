package codecool.java.handler;

import codecool.java.controller.CardController;
import codecool.java.controller.StudentController;
import codecool.java.helper.HttpResponse;
import codecool.java.model.CannotAffordCardException;
import codecool.java.model.Card;
import codecool.java.model.Student;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CardHandlerTest {
    static StudentController mockStudentController;
    static CardController mockCardController;
    static HttpResponse mockHttpResponse;
    static CookieHelper mockCookieHelper;
    static CardHandler cardHandler;
    static CardHandler mockCardHandler;

    @BeforeEach
    void setUp() {
        mockStudentController = mock(StudentController.class);
        mockCardController = mock(CardController.class);
        mockHttpResponse = mock(HttpResponse.class);
        mockCookieHelper = mock(CookieHelper.class);
        cardHandler = new CardHandler(mockCardController, mockStudentController, mockCookieHelper, mockHttpResponse);
        mockCardHandler = mock(CardHandler.class);
    }
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
    public void shouldRaiseCannotAffordCardExceptionWhenNotEnoughCoins() {
        // Arrange
        Student mockStudent = mock(Student.class);
        Card mockCard = mock(Card.class);
        // Act
        when(mockStudentController.getStudentCoins(mockStudent)).thenReturn(5);
        when(mockCard.getCost()).thenReturn(10);
        // Assert
        assertThrows(CannotAffordCardException.class, () ->{cardHandler.tryBuyCard(mockStudent,mockCard);});
    }

    @Test
    public void shouldCallManageSuccessfulTransactionWhenEnoughCoins() throws CannotAffordCardException {
        // Arrange
        Student mockStudent = mock(Student.class);
        Card mockCard = mock(Card.class);
        when(mockStudentController.getStudentCoins(mockStudent)).thenReturn(10);
        when(mockCard.getCost()).thenReturn(10);
        // Act
        cardHandler.tryBuyCard(mockStudent,mockCard);
        // Assert
        verify( mockStudentController).buyCard(mockStudent,mockCard);
        verify( mockStudentController).decreaseStudentCoins(mockStudent,mockCard.getCost());
    }
    @Test
    public void shouldCallRedirectToLoginWhenGETRequestedButCookieExpired() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(false);
        // Act
        cardHandler.handleGET(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).redirectToLoginPage(mockHttpExchange);
    }
    @Test
    public void shouldCallSendResponse200WhenGETRequestedAndCookieActive() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(true);
        when(mockCardController.getCardsJsonList()).thenReturn("{passed}");
        // Act
        cardHandler.handleGET(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).sendResponse200(mockHttpExchange,"{passed}");
    }
    @Test
    public void shouldCallRedirectToLoginWhenPOSTRequestedButCookieExpired() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(false);
        // Act
        cardHandler.handlePOST(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).redirectToLoginPage(mockHttpExchange);
    }
    @Test
    public void shouldCallSendResponse200WhenPOSTRequestedCookieActiveAffordabilityChecked() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        Student mockStudent = mock(Student.class);
        Card mockCard = mock(Card.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(true);
        when(cardHandler.getStudentBySessionId(mockHttpExchange)).thenReturn(mockStudent);
        when(cardHandler.getCardFromURI(mockHttpExchange)).thenReturn(mockCard);
        when(mockStudentController.getStudentCoins(mockStudent)).thenReturn(10);
        when(mockCard.getCost()).thenReturn(10);
        // Act
        cardHandler.handlePOST(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).sendResponse200(mockHttpExchange,"Card bought");
    }
    @Test
    public void shouldCallSendResponse403WhenPOSTRequestedCookieActiveButAffordabilityDenied() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        Student mockStudent = mock(Student.class);
        Card mockCard = mock(Card.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(true);
        when(cardHandler.getStudentBySessionId(mockHttpExchange)).thenReturn(mockStudent);
        when(cardHandler.getCardFromURI(mockHttpExchange)).thenReturn(mockCard);
        when(mockStudentController.getStudentCoins(mockStudent)).thenReturn(0);
        when(mockCard.getCost()).thenReturn(10);
        // Act
        cardHandler.handlePOST(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).sendResponse403(mockHttpExchange);
    }
}