package codecool.java.handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MentorStudentHandlerTest {
    @Test
    void loadAllStudents() {
        DbstudentDAO mockStudentDAO = Mockito.mock(DbstudentDAO.class);
        Student student1 = new Student("alex", "123", "Alex", "Smith", true);
        Student student2 = new Student("john", "123", "John", "Milton", true);
        Mockito.when(mockStudentDAO.loadAll()).thenReturn(Arrays.asList(student1, student2));
        Gson gson = new Gson();
        String studentList = gson.toJson(mockStudentDAO.loadAll());
        String expected = "[{\"id\":0,\"login\":\"alex\",\"password\":\"123\",\"name\":\"Alex\",\"surname\":\"Smith\",\"isActive\":true},{\"id\":0,\"login\":\"john\",\"password\":\"123\",\"name\":\"John\",\"surname\":\"Milton\",\"isActive\":true}]";
        assertEquals(expected, studentList);
    }
}