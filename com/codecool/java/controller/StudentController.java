package codecool.java.controller;

import codecool.java.dao.TransactionsDAO;
import codecool.java.model.Card;
import codecool.java.model.CardTransaction;
import codecool.java.model.QuestTransaction;
import codecool.java.model.Student;
import codecool.java.view.TerminalView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private CardDAO cardDAO;
    private QuestDAO questDAO;
    private TransactionsDAO transactionsDAO;
    private TerminalView terminalView;
    private List<Transaction> transactions;
    private List<Card> cards;
    private List<Quest> quests;

    public StudentController() throws SQLException, ClassNotFoundException {
        cardDAO = new DbCardDAO();
        questDAO = new DbQuestDAO();
        transactionsDAO = new DBtransactionsDAO();
        terminalView = new TerminalView();
    }

    public void run(int studentId) {
        String[] options = {"Show all cards", "Show all quests", "Show my transactions", "Buy a card", "Mark quest as achieved"};
        terminalView.displayOptions(options);
        int userInput = terminalView.getOptionInput(options.length);
        switch(userInput){
            case 1:
                showAllCards();
                break;
            case 2:
                showAllQuests();
                break;
            case 3:
                showTransactions();
                break;
            case 4:
                buyCard(studentId);
                break;
            case 5:
                submitQuest(studentId);
        }
    }

    private void showAllCards() {
        cards = getCards();
        terminalView.displayCards(cards);
    }

    private void showAllQuests() {
        quests = getQuests();
        terminalView.displayQuests(quests);
    }

    private List<Quest> getQuests() {
        List<Quest> quests = new ArrayList<>();
        return questDAO.loadAll();
    }

    private List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        return cardDAO.loadAll();
    }

    private List<Transaction> getTransactions() {
        transactions = new ArrayList<>();
        return transactionsDAO.loadAll();
    }

    private void showTransactions() {
        transactions = getTransactions();
        terminalView.displayCardTransactions(transactions);
    }

    private void buyCard(int studentId) {
        showAllCards();
        cards = getCards();
        int cardToBuyArrayIndex = terminalView.getOptionInput(cards.size()) - 1;
        Card cardToBuy = cards.get(cardToBuyArrayIndex);
        int cardToBuyDbIndex = cardToBuy.getId();
        CardTransaction cardTransaction = new CardTransaction(cardToBuyDbIndex, studentId, cardToBuy.getCost());
        transactionsDAO.addCardTransaction(cardTransaction);
    }

    private void submitQuest(int studentId) {
        showAllCards();
        quests = getQuests();
        int questToSubmitArrayIndex = terminalView.getOptionInput(quests.size()) - 1;
        Card questToSubmit = cards.get(questToSubmitArrayIndex);
        int questToSubmitDbIndex = questToSubmit.getId();
        QuestTransaction questTransaction = new QuestTransaction(questToSubmitDbIndex, studentId, questToSubmit.getCost());
        transactionsDAO.addQuestTransaction(questTransaction);
    }
}
