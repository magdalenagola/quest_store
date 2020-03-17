package codecool.java.handler;

import codecool.java.dao.DbConnectionDao;
import codecool.java.dao.DbstudentDAO;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.*;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class MentorStudentHandlerTest {

    DbstudentDAO dbstudentDAO = new DbstudentDAO();
    @Test
    void shouldCreateStudentFromJson() throws IOException {
        String stringData = "{\"id\":0,\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true}";
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        Mockito.when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
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
        mentorStudentHandler.handleAddStudent(httpExchange);
        Student studentById = dbstudentDAO.selectStudentByLogin("alex");
        assertTrue(studentById.isActive());
    }

    @Test
    void shouldDisableStudentFromDb() throws IOException {
        String id = String.valueOf(dbstudentDAO.selectStudentByLogin("alex").getId());
        String stringData = String.format("\"%s\"", id);
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        Mockito.when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        Mockito.when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        Mockito.when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        MentorStudentHandler mentorStudentHandler = new MentorStudentHandler();
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
        Mockito.when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        Mockito.when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        Mockito.when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
        MentorStudentHandler mentorStudentHandler = new MentorStudentHandler();
        mentorStudentHandler.handleUpdateStudent(httpExchange);
        String expected = "Doe";
        Student studentUpdated = dbstudentDAO.selectStudentById(student.getId());
        deleteTestStudent(student);
        assertEquals(expected, studentUpdated.getSurname());
    }

    void createTestStudent() {
        Student student = new Student("login", "test123", "name", "surname", true);
        dbstudentDAO.save(student);
    }

    void deleteTestStudent(Student student) {
        dbstudentDAO.delete(student);
    }
}