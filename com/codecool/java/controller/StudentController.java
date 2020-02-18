package codecool.java.controller;

import codecool.java.dao.*;
import codecool.java.model.*;
import codecool.java.view.TerminalView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentController {
    private CardDAO cardDAO;
    private QuestDAO questDAO;
    private TransactionsDAO transactionsDAO;
    private TerminalView terminalView;

    public StudentController() throws SQLException, ClassNotFoundException {
        cardDAO = new DbCardDAO();
        questDAO = new DbQuestDAO();
        transactionsDAO = new DbTransactionsDAO();
        terminalView = new TerminalView();
    }

    public void run(int studentId) {
        String[] options = {"Show all cards", "Show all quests", "Show my transactions", "Buy a card", "Mark quest as achieved"};
        terminalView.displayOptions(options);
        int userInput = terminalView.getOptionInput(options.length);
        switch(userInput){
            case 1:
                try {
                    showAllCards();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    showAllQuests();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    showTransactions();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    buyCard(studentId);
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    submitQuest(studentId);
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
        }
    }

    private void showAllCards() throws SQLException {
        List<Card> cards = getCards();
        terminalView.displayCards(cards);
    }

    private void showAllQuests() throws SQLException {
        List<Quest> quests = getQuests();
        terminalView.displayQuests(quests);
    }

    private List<Quest> getQuests() throws SQLException {
        return questDAO.loadAll();
    }

    private List<Card> getCards() throws SQLException {
        return cardDAO.loadAll();
    }

    private List<Transaction> getTransactions() throws SQLException {
        return transactionsDAO.loadAll();
    }

    private void showTransactions() throws SQLException, ClassNotFoundException {
        List<Transaction> transactions = getTransactions();
        terminalView.displayCardTransactions(transactions);
    }

    private void buyCard(int studentId) throws SQLException, ParseException {
        showAllCards();
        List<Card> cards = getCards();
        int cardToBuyIndex = terminalView.getOptionInput(cards.size()) - 1;
        Card cardToBuy = cards.get(cardToBuyIndex);
        int cardToBuyDbIndex = cardToBuy.getId();
        Date todayDate = getTodayDate();
        CardTransaction cardTransaction = new CardTransaction(cardToBuyDbIndex, studentId, (java.sql.Date) todayDate, cardToBuy.getCost());
        transactionsDAO.save(cardTransaction);
    }

    private Date getTodayDate() throws ParseException {
        String datePattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String stringDate = simpleDateFormat.format(new Date());
        Date todayDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringDate);
        return todayDate;
    }

    private void submitQuest(int studentId) throws SQLException, ParseException {
        showAllCards();
        List<Quest> quests = getQuests();
        int questToSubmitIndex = terminalView.getOptionInput(quests.size()) - 1;
        Quest questToSubmit = quests.get(questToSubmitIndex);
        int questToSubmitDbIndex = questToSubmit.getId();
        Date todayDate = getTodayDate();
        QuestTransaction questTransaction = new QuestTransaction(questToSubmitDbIndex, studentId, (java.sql.Date) todayDate, questToSubmit.getCost());
        transactionsDAO.save(questTransaction);
    }
}
