package codecool.java.handler;

import codecool.java.dao.DbQuestDAO;
import codecool.java.dao.QuestDAO;
import codecool.java.helper.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

class MentorQuestHandlerTest {
    static QuestDAO mockQuestDAO;
    static MentorQuestHandler mentorQuestHandler;
    static CookieHelper mockCookieHelper;
    static HttpResponse mockHttpResponse;

    @BeforeEach
    void setUp(){
        mockQuestDAO = mock(DbQuestDAO.class);
        mockCookieHelper = mock(CookieHelper.class);
        mockHttpResponse = mock(HttpResponse.class);
        mentorQuestHandler = new MentorQuestHandler(mockQuestDAO, mockCookieHelper, mockHttpResponse);
    }
    @Test
    public void shouldCallRedirectToLoginWhenGETRequestedButCookieExpired() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(false);
        // Act
        mentorQuestHandler.handleGET(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).redirectToLoginPage(mockHttpExchange);
    }

    @Test
    public void shouldCallSendResponse200WhenGETRequestedAndCookieActive() throws IOException {
        // Arrange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        when(mockCookieHelper.isCookiePresent(mockHttpExchange)).thenReturn(true);
        when(mockQuestDAO.loadAll()).thenReturn(Collections.singletonList("quests"));
        // Act
        mentorQuestHandler.handleGET(mockHttpExchange);
        // Assert
        verify(mockHttpResponse).sendResponse200(mockHttpExchange,"[\"quests\"]");
    }
}