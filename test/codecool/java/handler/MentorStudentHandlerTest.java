package codecool.java.handler;

import codecool.java.dao.DbstudentDAO;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MentorStudentHandlerTest {


    @Test
    void shouldCreateStudentFromJson() throws IOException {
        String stringData = "{\"id\":0,\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true}";
        HttpExchange httpExchange = new HttpExchange() {
            @Override
            public Headers getRequestHeaders() {
                return null;
            }

            @Override
            public Headers getResponseHeaders() {
                return null;
            }

            @Override
            public URI getRequestURI() {
                return null;
            }

            @Override
            public String getRequestMethod() {
                return null;
            }

            @Override
            public HttpContext getHttpContext() {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getRequestBody() {
                InputStream targetStream = new ByteArrayInputStream(stringData.getBytes());
                return targetStream;
            }

            @Override
            public OutputStream getResponseBody() {
                return null;
            }

            @Override
            public void sendResponseHeaders(int rCode, long responseLength) throws IOException {

            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public int getResponseCode() {
                return 0;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public void setStreams(InputStream i, OutputStream o) {

            }

            @Override
            public HttpPrincipal getPrincipal() {
                return null;
            }
        };
        MentorStudentHandler mentorStudentHandler = new MentorStudentHandler();
        Gson gson = new Gson();
        Student expected = gson.fromJson(stringData, Student.class);
        assertTrue(new ReflectionEquals(expected).matches(mentorStudentHandler.receiveStudentFromFront(httpExchange)));
    }

    @Test
    void shouldAddStudentFromDb() throws IOException {
        String stringData = "{\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true}";
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        Mockito.when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        Mockito.when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        Mockito.when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        MentorStudentHandler mentorStudentHandler = new MentorStudentHandler();
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        mentorStudentHandler.handleAddStudent(httpExchange);
        Student studentById = dbstudentDAO.selectStudentByLogin("alex");
        assertTrue(studentById.isActive());
    }

    @Test
    void shouldDisableStudentFromDb() throws IOException {
        String stringData = "{\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true}";
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        Mockito.when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        Mockito.when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        Mockito.when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        MentorStudentHandler mentorStudentHandler = new MentorStudentHandler();
        mentorStudentHandler.handleDeleteStudent(httpExchange);
        Student studentById = dbstudentDAO.selectStudentById(mentorStudentHandler.receiveStudentFromFront(httpExchange).getId());
        assertFalse(studentById.isActive());
    }
}