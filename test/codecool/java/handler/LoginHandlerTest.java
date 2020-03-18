package codecool.java.handler;

import codecool.java.dao.NotInDatabaseException;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginHandlerTest {
    LoginHandler loginHandler = new LoginHandler();
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

}