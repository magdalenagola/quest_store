package handler;

import codecool.java.dao.DbMentorDAO;
import codecool.java.handler.ManagerMentorsHandler;
import codecool.java.model.Mentor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManagerMentorsHandlerTest {
    static ManagerMentorsHandler handler = new ManagerMentorsHandler();

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
}