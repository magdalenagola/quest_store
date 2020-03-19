package codecool.java.handler;

import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MentorStudentHandlerTest {

    DbstudentDAO dbstudentDAO = new DbstudentDAO();
    CookieHelper cookieHelper = mock(CookieHelper.class);
    HttpResponse httpResponse = mock(HttpResponse.class);
    MentorStudentHandler mentorStudentHandler = new MentorStudentHandler();

    @Test
    void shouldCreateStudentFromJson() throws IOException {
        String stringData = "{\"id\":0,\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true}";
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));

        Gson gson = new Gson();
        Student expected = gson.fromJson(stringData, Student.class);
        assertTrue(new ReflectionEquals(expected).matches(mentorStudentHandler.receiveStudentFromFront(httpExchange)));
    }

    @Test
    void shouldAddStudentFromDb() throws IOException {
        String stringData = "{\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true}";
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        mentorStudentHandler.handleAddStudent(httpExchange);
        Student studentById = dbstudentDAO.selectStudentByLogin("alex");
        assertTrue(studentById.isActive());
    }

    @Test
    void shouldDisableStudentFromDb() throws IOException {
        String id = String.valueOf(dbstudentDAO.selectStudentByLogin("alex").getId());
        String stringData = String.format("\"%s\"", id);
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        mentorStudentHandler.handleDeleteStudent(httpExchange);
        Student studentById = dbstudentDAO.selectStudentById(Integer.parseInt(id));
        assertFalse(studentById.isActive());
    }

    @Test
    void shouldUpdateStudent() throws IOException {
        createTestStudent();
        Student student = dbstudentDAO.selectStudentByLogin("login");
        String stringId = "\"id\":" + "\"" + student.getId() + "\",";
        String stringData = "{" + stringId + "\"login\":\"login\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Doe\"}";
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        mentorStudentHandler.handleUpdateStudent(httpExchange);
        String expected = "Doe";
        Student studentUpdated = dbstudentDAO.selectStudentById(student.getId());
        deleteTestStudent(student);
        assertEquals(expected, studentUpdated.getSurname());
    }

    @Test
    public void shouldCallSendResponse200WhenGetStudentList() throws IOException {
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.isCookiePresent(httpExchange)).thenReturn(true);
        String expected = mentorStudentHandler.getStudentList();
        mentorStudentHandler.handleGET(httpExchange);
        verify(httpResponse).sendResponse200(httpExchange,expected);
    }

    @Test
    void shouldInvokeGet() throws IOException {
        MentorStudentHandler mentorStudentHandlerSpy = spy(new MentorStudentHandler(cookieHelper, httpResponse));
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        mentorStudentHandlerSpy.handle(httpExchange);
        verify(mentorStudentHandlerSpy).handleGET(httpExchange);
    }

    void createTestStudent() {
        Student student = new Student("login", "test123", "name", "surname", true);
        dbstudentDAO.save(student);
    }

    void deleteTestStudent(Student student) {
        dbstudentDAO.delete(student);
    }
}