package codecool.java.handler;

import codecool.java.dao.DbQuestDAO;
import codecool.java.model.Quest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class StudentQuestHandlerTest {

    @Test
    void getQuests() {
        List<Quest> quests = createQuestList();
        StudentQuestHandler studentQuestHandler = new StudentQuestHandler();
        String expected = "[{\"category\":\"testing\",\"id\":1,\"title\":\"testTitle1\",\"description\":\"testDesc1\",\"image\":\"testImage1\",\"isActive\":true,\"cost\":12},{\"category\":\"testing\",\"id\":2,\"title\":\"testTitle2\",\"description\":\"testDesc2\",\"image\":\"testImage2\",\"isActive\":true,\"cost\":14}]";
        String actual = studentQuestHandler.getQuests(quests);
        assertEquals(expected, actual);
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