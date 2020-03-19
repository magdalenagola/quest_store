package codecool.java.handler;

import codecool.java.controller.LoginController;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import codecool.java.model.User;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginHandlerTest {
    static CookieHelper mockCookieHelper;
    static HttpResponse mockHttpResponse;
    static HttpExchange mockHttpExchange;
    static LoginHandler loginHandler;
    static LoginHandler mockLoginHandler;
    static LoginController mockLoginController;


    @BeforeEach
    void setUp(){
        mockCookieHelper = mock(CookieHelper.class);
        mockHttpResponse = mock(HttpResponse.class);
        mockHttpExchange = mock(HttpExchange.class);
        mockLoginController = mock(LoginController.class);
        loginHandler = new LoginHandler(mockCookieHelper, mockHttpResponse, mockLoginController);
        mockLoginHandler = mock(LoginHandler.class);

    }

    @Test
    public void shouldThrowNotInDatabaseExceptionWhenUserDoesNotExist(){
        String data = "[notExistingUser, notExistingPassword]";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        assertThrows(NotInDatabaseException.class, ()-> {
            loginHandler.getUserData(inputStream);
        });
    }

    @Test
    public void shouldReturnStudentClassObjectWhenLoginAndPasswordForStudentUserProvided() throws IOException, NotInDatabaseException {
        String data = "[\"student\",\"123\"]";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        assertEquals("Student", loginHandler.getUserData(inputStream).getClass().getSimpleName());
    }

    @Test
    public void shouldSendResponse200AndCreateNewCookieWhenUserSuccessfullyLoggedIn() throws IOException, NotInDatabaseException {
        // Arrange
        String data = "[\"student\",\"123\"]";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        when(mockHttpExchange.getRequestBody()).thenReturn(inputStream);
        User student = new Student(2,"student", "123", "Szczepan", "Topolski", true);
        when(mockLoginController.authenticate("student", "123")).thenReturn(student);
        // Act
        loginHandler.loginUser(mockHttpExchange);
        // Assert
        verify(mockCookieHelper).createNewCookie(mockHttpExchange, student);
        verify(mockHttpResponse).sendResponse200(mockHttpExchange, "Student");
    }

    @Test
    public void shouldSendResponse404WhenUserFailedToLogin() throws IOException, NotInDatabaseException {
        // Arrange
        String data = "[\"student\",\"123\"]";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        when(mockHttpExchange.getRequestBody()).thenReturn(inputStream);
        when(mockLoginController.authenticate("student", "123")).thenThrow(NotInDatabaseException.class);
        // Act
        loginHandler.loginUser(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).sendResponse404(mockHttpExchange);
    }

}