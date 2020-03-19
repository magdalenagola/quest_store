package codecool.java.handler;

import codecool.java.dao.DbMentorDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Mentor;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManagerMentorsHandlerTest {
    static ManagerMentorsHandler handler = new ManagerMentorsHandler();
    DbMentorDAO dao = new DbMentorDAO();
    CookieHelper cookieHelper = mock(CookieHelper.class);
    HttpResponse httpResponse = mock(HttpResponse.class);

    @Test
    void shouldReturnJsonListWithOneMentor() {
        // Arrange
        DbMentorDAO mockDao = mock(DbMentorDAO.class);
        Mentor mentor = new Mentor(1, "benio", "123", "benio", "beniński", "java", true);
        List<Mentor> mentorList = new ArrayList<>();
        mentorList.add(mentor);
        when(mockDao.loadAllActive()).thenReturn(mentorList);
        String expected = "[{\"id\":1,\"login\":\"benio\",\"password\":\"123\",\"name\":\"benio\",\"surname\":\"beniński\",\"primarySkill\":\"java\",\"isActive\":true,\"earnings\":0}]";
        // Act
        String actual = handler.getMentorsJson(mockDao.loadAllActive());
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnJsonListWithTwoMentors() {
        // Arrange
        DbMentorDAO mockDao = mock(DbMentorDAO.class);
        Mentor[] mentorsArr = {
                new Mentor(1, "benio", "123", "benio", "beniński", "java", true),
                new Mentor(2, "gienio", "234", "gieniu", "gieniński", "php", true)};
        List<Mentor> mentorList = new ArrayList<>();
        mentorList.addAll(Arrays.asList(mentorsArr));
        when(mockDao.loadAllActive()).thenReturn(mentorList);
        String expected = "[{\"id\":1,\"login\":\"benio\",\"password\":\"123\",\"name\":\"benio\",\"surname\":\"beniński\",\"primarySkill\":\"java\",\"isActive\":true,\"earnings\":0},{\"id\":2,\"login\":\"gienio\",\"password\":\"234\",\"name\":\"gieniu\",\"surname\":\"gieniński\",\"primarySkill\":\"php\",\"isActive\":true,\"earnings\":0}]";
        // Act
        String actual = handler.getMentorsJson(mockDao.loadAllActive());
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCallSendResponse200WhenGetMentorsList() throws IOException {
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.isCookiePresent(httpExchange)).thenReturn(true);
        handler.handleGET(httpExchange);
    }

    /**
    @Test
    public void shouldUpdateMentor() {
    // Arrange
        String expected = "updated login";
        createTestMentor();
        Mentor mentor = dao.selectMentorByLoginWithId("MentorLogin@cc.com");
        String stringId = "\"id\":" + "\"" + mentor.getId() + "\",";
        String stringData = "{" + stringId + "\"login\":\"updated login\",\"password\":\"updated pass\",\"name\":\"updated name\",\"surname\":\"updated surname\"}";
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(stringData.getBytes()));
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream(21));
        when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
    // Act
        try {
            handler.handleUpdateMentor(httpExchange);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mentor updatedMentor = dao.selectMentorById(mentor.getId());
        deleteTestMentor();
        String actual = updatedMentor.getLogin();
        // Assert
        assertEquals(expected, actual);
    }
     */

    /**
    void createTestMentor() {
        Mentor mentor = new Mentor("MentorLogin@cc.com", "menPass", "Mentor", "Mentorino", "Niczego nie umie", true);
        dao.save(mentor);
    }

    void deleteTestMentor() {
        Mentor mentor = new Mentor("MentorLogin@cc.com", "menPass", "Mentor", "Mentorino", "Niczego nie umie", true);
        dao.delete(mentor);
    }
     */

}