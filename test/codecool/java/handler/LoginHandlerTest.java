package codecool.java.handler;

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


    @BeforeEach
    void setUp(){
        mockCookieHelper = mock(CookieHelper.class);
        mockHttpResponse = mock(HttpResponse.class);
        mockHttpExchange = mock(HttpExchange.class);
        loginHandler = new LoginHandler(mockCookieHelper, mockHttpResponse);
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
    public void shouldReturnStudentClassOvjectWhenLoginAndPasswordForStudentUserProvided() throws IOException, NotInDatabaseException {
        String data = "[\"student\",\"123\"]";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        assertEquals("Student", loginHandler.getUserData(inputStream).getClass().getSimpleName());
    }

    @Test
    public void shouldSendResponse200AndCreateNewCookieWhenUserSuccessfullyLoggedIn() throws IOException, NotInDatabaseException {
        // Arrange
        User student = new Student("login", "xxxxxx", "name", "surname", true);
        // Act
        loginHandler.handleLogin(mockHttpExchange, student);
        // Assert
        verify(mockCookieHelper).createNewCookie(mockHttpExchange, student);
        verify(mockHttpResponse).sendResponse200(mockHttpExchange, "Student");
    }

}