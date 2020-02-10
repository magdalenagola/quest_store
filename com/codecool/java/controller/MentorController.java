package codecool.java.controller;

import codecool.java.dao.StudentDAO;
import codecool.java.dao.TransactionsDAO;
import codecool.java.model.Card;
import codecool.java.model.Mentor;
import codecool.java.model.Student;
import codecool.java.view.Display;
import codecool.java.view.TerminalView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MentorController {
    Display view = new TerminalView();
    StudentDAO studentDAO = new DbstudentDAO();
    QuestDAO questDAO = new DbquestDAO();
    CardDAO cardDAO = new DbcardDAO();
    TransactionsDAO transactionsDAO = new DbtransactionsDAO();
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
        List<Student> students= studentDAO.loadAll();
        for(Student student:students){
            view.displayMessage(student.toString());
            view.displayMessage("Coins:"+String.valueOf(studentDAO.getCoins(student)));
        }
    }

    private void rateAssigment() {
        List<Transactions> notApprovedTransactions = transactionsDAO.loadAllNotApproved();
        view.displayCardTransactions(notApprovedTransactions);
        int studentTransactionIndex = view.getOptionInput(notApprovedTransactions.size());
        QuestTransaction studentTransaction = notApprovedTransactions.get(studentTransactionIndex-1);
        try {
            studentTransaction.setTransactionDate(getTodayDate());
            transactionsDAO.updateStudentQuest(studentTransaction);
        } catch (ParseException e) {
            view.displayErrorMessage(e);
        }
    }

    private Date getTodayDate() throws ParseException {
        String datePattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String stringDate = simpleDateFormat.format(new Date());
        Date todayDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringDate);
        return todayDate;
    }
    private void updateCard() {
        ArrayList<Card> cards = cardDAO.loadAll();
        view.displayCards(cards);
        int cardChoice = view.getOptionInput(cards.size());
        Card card = cards.get(cardChoice-1);
        String[] options = {"Title", "Description","Image","Quantity"};
        view.displayOptions(options);
        int attributeChoice = view.getOptionInput(options.length);
        String newValue = "";
        switch(attributeChoice){
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
                card.setImage(newValue);
                break;
            case 4:
                newValue = view.getStringInput();
                card.setQuantity(newValue);
                break;
        }
        cardDAO.update(card);
    }

    private void addCard() {
        String[] options = {"Title", "Description","Image","Quantity","Cost"};
        String[] inputs = view.getInputs(options);
        Card card = new Card(inputs[0],inputs[1],inputs[2],inputs[3],true);
        cardDAO.save(card);
    }

    private void addQuest() {
        String[] options = {"Title", "Description","Image","Category","Cost"};
        String[] inputs = view.getInputs(options);
        Quest quest = new Quest(inputs[0],inputs[1],inputs[2],inputs[3],true);
        questDAO.save(quest);
    }

    private void addStudent() {
        String[] options = {"Login", "Password","Name","Surname"};
        String[] inputs = view.getInputs(options);
        Student student = new Student(inputs[0],inputs[1],inputs[2],inputs[3],true);
        studentDAO.save(student);
    }
}
