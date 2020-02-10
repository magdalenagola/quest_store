package codecool.java.controller;

import codecool.java.dao.TransactionsDAO;
import codecool.java.model.Card;
import codecool.java.model.CardTransaction;
import codecool.java.model.QuestTransaction;
import codecool.java.view.TerminalView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentController {
    private CardDAO cardDAO;
    private QuestDAO questDAO;
    private TransactionsDAO transactionsDAO;
    private TerminalView terminalView;
    private List<Transaction> transactions;
    private List<Card> cards;
    private List<Quest> quests;
    private QuestTransaction questTransaction;

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
        int cardToBuyIndex = terminalView.getOptionInput(cards.size()) - 1;
        Card cardToBuy = cards.get(cardToBuyIndex);
        int cardToBuyDbIndex = cardToBuy.getId();
        Date todayDate = getTodayDate();
        CardTransaction cardTransaction = new CardTransaction(cardToBuyDbIndex, studentId, todayDate, cardToBuy.getCost());
        transactionsDAO.addCardTransaction(cardTransaction);
    }

    private Date getTodayDate() {
        String datePattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String stringDate = simpleDateFormat.format(new Date());
        Date todayDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringDate);
        return todayDate;
    }

    private void submitQuest(int studentId) {
        showAllCards();
        quests = getQuests();
        int questToSubmitIndex = terminalView.getOptionInput(quests.size()) - 1;
        Card questToSubmit = cards.get(questToSubmitIndex);
        int questToSubmitDbIndex = questToSubmit.getId();
        Date todayDate = getTodayDate();
        QuestTransaction questTransaction = new QuestTransaction(questToSubmitDbIndex, studentId, todayDate, questToSubmit.getCost());
        transactionsDAO.addQuestTransaction(questTransaction);
    }
}
