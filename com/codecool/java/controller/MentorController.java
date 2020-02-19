package codecool.java.controller;

import codecool.java.dao.*;
import codecool.java.model.*;
import codecool.java.view.*;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MentorController {
    Display view;
    public MentorController(){
        view = new TerminalView();
    }
    public void run(){
        String[] options = {"Add new codecooler.","Add new quest.","Add new card","Update card",
        "Rate student's assigment","Display students statistics"};
        view.displayOptions(options);
        int userInput = view.getOptionInput(options.length);
        switch(userInput){
            case 1:
                addStudent();
                break;
            case 2:
                addQuest();
                break;
            case 3:
                addCard();
                break;
            case 4:
                updateCard();
                break;
            case 5:
                rateAssigment();
                break;
            case 6:
                displayStatistics();
                break;
        }
    }

    private void displayStatistics() {
        try {
            StudentDAO studentDAO = new DbstudentDAO();
            List<Student> students = studentDAO.loadAll();
            for (Student student : students) {
                view.displayMessage(student.toString());
                view.displayMessage("Coins:" + String.valueOf(studentDAO.getCoins(student)));
            }
        }catch(SQLException | ClassNotFoundException e){
            view.displayErrorMessage(e);
        }
    }

    private void rateAssigment() {
        try {
            TransactionsDAO transactionsDAO = new DbTransactionsDAO();
            List<Transaction> notApprovedTransactions = transactionsDAO.loadAllNotApproved();
            view.displayCardTransactions(notApprovedTransactions);
            int studentTransactionIndex = view.getOptionInput(notApprovedTransactions.size());
            Transaction studentTransaction = notApprovedTransactions.get(studentTransactionIndex-1);
            studentTransaction.setDate((java.sql.Date) getTodayDate());
            transactionsDAO.update(studentTransaction);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            view.displayErrorMessage(e);
        }
    }

    private Date getTodayDate() throws ParseException {
        String datePattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String stringDate = simpleDateFormat.format(new Date());
        Date todayDate = (Date) new SimpleDateFormat("MM-dd-yyyy").parse(stringDate);
        return todayDate;
    }
    private void updateCard() {
        try {
            CardDAO cardDAO = new DbCardDAO();
            List<Card> cards = cardDAO.loadAll();
            view.displayCards(cards);
            int cardChoice = view.getOptionInput(cards.size());
            Card card = cards.get(cardChoice - 1);
            String[] options = {"Title", "Description", "Image", "Quantity"};
            view.displayOptions(options);
            int attributeChoice = view.getOptionInput(options.length);
            String newValue = "";
            switch (attributeChoice) {
                case 1:
                    newValue = view.getStringInput();
                    card.setTitle(newValue);
                    break;
                case 2:
                    newValue = view.getStringInput();
                    card.setDescription(newValue);
                    break;
                case 3:
                    newValue = view.getStringInput();
                    card.setImageName(newValue);
                    break;
                case 4:
                    newValue = view.getStringInput();
                    card.setQuantity(Integer.parseInt(newValue));
                    break;
            }
            cardDAO.update(card);
        }catch(SQLException | ClassNotFoundException e){
            view.displayErrorMessage(e);
        }
    }

    private void addCard() {
        try{
            CardDAO cardDAO = new DbCardDAO();
            String[] options = {"Cost","Description", "Image","Quantity","Title"};
            String[] inputs = view.getInputs(options);
            Card card = new Card(Integer.parseInt(inputs[0]),inputs[1],inputs[2],true,Integer.parseInt(inputs[3]),inputs[4]);
            cardDAO.save(card);
        }catch(SQLException | ClassNotFoundException e){
            view.displayErrorMessage(e);
        }
    }

    private void addQuest() {
        String[] options = {"Title", "Description","Image","Cost","Category"};
        String[] inputs = view.getInputs(options);
        try{
        QuestDAO questDAO = new DbQuestDAO();
        Quest quest = new Quest(0,inputs[0],inputs[1],inputs[2],Integer.parseInt(inputs[3]),true,Integer.parseInt(inputs[4]),inputs[5]);
        questDAO.save(quest);
        }catch(SQLException | ClassNotFoundException e){
            view.displayErrorMessage(e);
        }
    }

    private void addStudent() {
        try{
        StudentDAO studentDAO = new DbstudentDAO();
        String[] options = {"Login", "Password","Name","Surname"};
        String[] inputs = view.getInputs(options);
        Student student = new Student(inputs[0],inputs[1],inputs[2],inputs[3],true);
        studentDAO.save(student);
        }catch(SQLException | ClassNotFoundException e){
            view.displayErrorMessage(e);
        }

    }
}
