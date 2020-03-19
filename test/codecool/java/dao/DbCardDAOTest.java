package codecool.java.dao;

import codecool.java.model.Card;
import codecool.java.model.DatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DbCardDAOTest {
    private DbCardDAO dao = new DbCardDAO();

    @BeforeAll
    public static void setDbToTest() {
        DatabaseConnection.INSTANCE.setEnv("test");
    }

    @Test
    public void selectCardById() {
        Card expected = new Card(11, 55, "test desc", "test image", true, 1, "test title");
        Card actual = dao.selectCardById(expected.getId());
        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    void enableAllCards() {
        List<Card> cards = dao.loadAll();

    }

    @Test
    void disableAllCards() {
    }

    @Test
    void save() {
    }

    @Test
    void loadAll() {
    }

    @Test
    void update() {
    }

    @Test
    void disable() {
    }

    @Test
    void activate() {
    }

    @Test
    void getCardsByStudent() {
    }
}