package codecool.java.handler;

import codecool.java.dao.DbQuestDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Quest;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class StudentQuestHandlerTest {

    CookieHelper cookieHelper = mock(CookieHelper.class);
    HttpResponse httpResponse = mock(HttpResponse.class);
    StudentQuestHandler studentQuestHandler = new StudentQuestHandler(cookieHelper, httpResponse);
    DbQuestDAO dbQuestDAO = new DbQuestDAO();

    @Test
    void getQuests() {
        List<Quest> quests = createQuestList();
        String expected = "[{\"category\":\"testing\",\"id\":1,\"title\":\"testTitle1\",\"description\":\"testDesc1\",\"image\":\"testImage1\",\"isActive\":true,\"cost\":12},{\"category\":\"testing\",\"id\":2,\"title\":\"testTitle2\",\"description\":\"testDesc2\",\"image\":\"testImage2\",\"isActive\":true,\"cost\":14}]";
        String actual = studentQuestHandler.getQuests(quests);
        assertEquals(expected, actual);
    }

    @Test
    void shouldSendResponse200WhenHandleGet() throws IOException {
        HttpExchange httpExchange = mock(HttpExchange.class);
        when(cookieHelper.isCookiePresent(httpExchange)).thenReturn(true);
        String expected = studentQuestHandler.getQuests(dbQuestDAO.loadAllActive());
        studentQuestHandler.handleGET(httpExchange);
        verify(httpResponse).sendResponse200(httpExchange, expected);
    }

    private List<Quest> createQuestList() {
        Quest quest1 = new Quest(1, "testTitle1", "testDesc1", "testImage1", true, 12, "testing");
        Quest quest2 = new Quest(2, "testTitle2", "testDesc2", "testImage2", true, 14, "testing");
        List<Quest> quests = new ArrayList<>();
        quests.add(quest1);
        quests.add(quest2);
        return quests;
    }
}