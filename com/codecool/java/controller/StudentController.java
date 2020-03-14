package codecool.java.controller;

import codecool.java.dao.*;
import codecool.java.model.*;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StudentController {
    private CardDAO cardDAO;
    private TransactionsDAO transactionsDAO;

    public StudentController(){
        cardDAO = new DbCardDAO();
        transactionsDAO = new DbTransactionsDAO();
    }

    public void buyCard(int studentId, int cardId){
        Card cardToBuy = null;
        List<Card> cards = cardDAO.loadAll();
        cardToBuy = findCardById(cardId, cardToBuy, cards);
        Date todayDate = null;
        try {
            todayDate = getTodayDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sDate = new java.sql.Date(todayDate.getTime());
        CardTransaction cardTransaction = new CardTransaction(cardToBuy, studentId, sDate, cardToBuy.getCost());
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
}
