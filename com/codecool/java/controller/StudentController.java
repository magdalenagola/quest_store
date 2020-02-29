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

    public void run(Student student) {
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
                    showTransactions(student);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
//                try {
//                    buyCard(student.getId());
//                } catch (SQLException | ParseException e) {
//                    e.printStackTrace();
//                }
                 break;
            case 5:
                try {
                    submitQuest(student.getId());
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

    private List<Transaction> getAllTransactions() throws SQLException {
        return transactionsDAO.loadAll();
    }

    private List<Transaction> getUserTransactions(Student student) throws SQLException {
        return transactionsDAO.displayAllTransactionsByStudent(student);
    }
    private void showTransactions(Student student) throws SQLException {
        List<Transaction> transactions = getUserTransactions(student);
        terminalView.displayCardTransactions(transactions);
    }

    public void buyCard(int studentId, int cardId) throws SQLException, ParseException {
        Card cardToBuy = null;
        List<Card> cards = getCards();
        cardToBuy = findCardById(cardId, cardToBuy, cards);
        int cardToBuyDbIndex = cardToBuy.getId();
        Date todayDate = getTodayDate();
        java.sql.Date sDate = new java.sql.Date(todayDate.getTime());
        CardTransaction cardTransaction = new CardTransaction(cardToBuyDbIndex, studentId, sDate, cardToBuy.getCost());
        transactionsDAO.save(cardTransaction);
    }

    public Card findCardById(int cardId, Card cardToBuy, List<Card> cards) {
        for(Card card :cards){
            if (card.getId() == cardId){
                cardToBuy = card;
            }
        }
        return cardToBuy;
    }

    private Date getTodayDate() throws ParseException {
        String datePattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String stringDate = simpleDateFormat.format(new Date());
        Date todayDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringDate);
        return todayDate;
    }

    private void submitQuest(int studentId) throws SQLException, ParseException {
        showAllQuests();
        List<Quest> quests = getQuests();
        int questToSubmitIndex = terminalView.getOptionInput(quests.size()) - 1;
        Quest questToSubmit = quests.get(questToSubmitIndex);
        int questToSubmitDbIndex = questToSubmit.getId();
        Date todayDate = getTodayDate();
        java.sql.Date sDate = new java.sql.Date(todayDate.getTime());
        QuestTransaction questTransaction = new QuestTransaction(questToSubmitDbIndex, studentId, sDate, questToSubmit.getCost());
        transactionsDAO.save(questTransaction);
    }
}
