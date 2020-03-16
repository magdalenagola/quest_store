package codecool.java.controller;

import codecool.java.dao.*;
import codecool.java.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StudentController {
    private TransactionsDAO transactionsDAO;

    public StudentController(TransactionsDAO transactionsDAO){
        this.transactionsDAO = transactionsDAO;
    }

    public void buyCard(Student student, Card cardToBuy){
        Date todayDate = getTodayDate();
        java.sql.Date sDate = new java.sql.Date(todayDate.getTime());
        CardTransaction cardTransaction = new CardTransaction(cardToBuy, student.getId(), sDate, cardToBuy.getCost());
        transactionsDAO.save(cardTransaction);
    }

    private Date getTodayDate(){
        String datePattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String stringDate = simpleDateFormat.format(new Date());
        Date todayDate = null;
        try {
            todayDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return todayDate;
    }
}
