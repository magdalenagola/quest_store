package codecool.java.controller;

import codecool.java.dao.DbCardDao;
import codecool.java.dao.DbTransactionsDAO;
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

    public StudentController() throws SQLException, ClassNotFoundException {
        cardDAO = new DbCardDao();
        questDAO = new DbQuestDao();
        transactionsDAO = new DbTransactionsDAO();
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
        List<Card> cards = getCards();
        terminalView.displayCards(cards);
    }

    private void showAllQuests() {
        List<Quest> quests = getQuests();
        terminalView.displayQuests(quests);
    }

    private List<Quest> getQuests() {
        return questDAO.loadAll();
    }

    private List<Card> getCards() {
        return cardDAO.loadAll();
    }

    private List<Transaction> getTransactions() {
        return transactionsDAO.loadAll();
    }

    private void showTransactions() {
        List<Transaction> transactions = getTransactions();
        terminalView.displayCardTransactions(transactions);
    }

    private void buyCard(int studentId) {
        showAllCards();
        List<Card> cards = getCards();
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
        List<Quest> quests = getQuests();
        int questToSubmitIndex = terminalView.getOptionInput(quests.size()) - 1;
        Quest questToSubmit = quests.get(questToSubmitIndex);
        int questToSubmitDbIndex = questToSubmit.getId();
        Date todayDate = getTodayDate();
        QuestTransaction questTransaction = new QuestTransaction(questToSubmitDbIndex, studentId, todayDate, questToSubmit.getCost());
        transactionsDAO.save(questTransaction);
    }
}
