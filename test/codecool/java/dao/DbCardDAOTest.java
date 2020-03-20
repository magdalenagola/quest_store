package codecool.java.dao;

import codecool.java.model.Card;
import codecool.java.model.DatabaseConnection;
import codecool.java.model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.*;

class DbCardDAOTest {
    private static final DbCardDAO DAO = new DbCardDAO();
    private static final Student BENIO = new Student(4, "TestBenio", "123", "123", "213", true);
    private static final Card TEST_CARD = new Card(11, 55, "test desc", "test image", true, 1, "test title");
    private static final Card SECOND_TEST_CARD = new Card(12, 1, "before_update", "before_update", true, 1, "before_update");

    @BeforeAll
    public static void setDbToTest() {
        DatabaseConnection.INSTANCE.setEnv("test");
        DAO.update(TEST_CARD);
        DAO.update(SECOND_TEST_CARD);
        }

    @Test
    public void shouldDisableAllCards() {
        DAO.disableAllCards();
        Card actual = DAO.selectCardById(TEST_CARD.getId());
        Card secondActual = DAO.selectCardById(TEST_CARD.getId());
        assertFalse(actual.isActive());
        assertFalse(secondActual.isActive());
        DAO.enableAllCards();
    }

    @Test
    void shouldSaveCard() {
        String uuid = generateRandomUUID();
        Card benioKarta = new Card(15, "benio karta", "benio.jpg", false, 99, uuid);
        DAO.save(benioKarta);
        List<Card> cards = DAO.loadAll();
        Optional<Card> first = cards.stream()
                .filter(compareTo(benioKarta))
                .findFirst();
        assertTrue(first.isPresent());
    }

    private Predicate<Card> compareTo(Card benioKarta) {
        return card -> card.getTitle().equals(benioKarta.getTitle());
    }

    @Test
    void shouldUpdateCard() {
        Card expected = new Card(TEST_CARD.getId(), TEST_CARD.getCost(), TEST_CARD.getDescription(), TEST_CARD.getImage(), TEST_CARD.isActive(), TEST_CARD.getQuantity(), TEST_CARD.getTitle());
        DAO.update(expected);
        Card actual = DAO.selectCardById(TEST_CARD.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldSetIsActiveCorrectly() {
        DAO.disable(TEST_CARD);
        Card actualDisabled = DAO.selectCardById(TEST_CARD.getId());
        assertFalse(actualDisabled.isActive());
        DAO.activate(TEST_CARD);
        Card actualEnabled = DAO.selectCardById(TEST_CARD.getId());
        assertTrue(actualEnabled.isActive());
    }

    @Test
    void shouldGetAllCardsOwnedByBenio() {
        int expected = 14;
        int actual = DAO.getCardsByStudent(BENIO).size();
        assertEquals(expected, actual);
    }

    private String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}