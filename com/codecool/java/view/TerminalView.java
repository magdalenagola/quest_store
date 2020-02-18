package codecool.java.view;

import codecool.java.model.*;

import java.sql.SQLException;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Scanner;

public class TerminalView implements Display{
    public void displayOptions(String[] options) {
        for(int i = 0; i < options.length; i++){
            System.out.println(String.format("(%d) %s", i+1, options[i]));
        }
    }

    public int getOptionInput(int maxOptionsNumber) {
        Scanner scan = new Scanner(System.in);
        String userInput = null;
        do {
            displayMessage("Your choice: ");
            userInput = scan.nextLine();
        }while(!isInputValid(userInput, maxOptionsNumber));
        return Integer.parseInt(userInput);
    }

    @Override
    public String[] getInputs(String[] options) {
        String[] inputs = new String[options.length];
        Scanner scan = new Scanner(System.in);
        displayMessage("Fill all fields");
        for(int i = 0; i< options.length; i++){
            displayMessage(options[i]+": ");
            inputs[i] = scan.nextLine();
        }
        return inputs;
    }

    @Override
    public String getStringInput() {
        String input ="";
        Scanner scan = new Scanner(System.in);
        input = scan.nextLine();
        return input;
    }

    private boolean isInputValid(String userInput,int optionsLength){
        try{
            int userChoice = Integer.parseInt(userInput);
            return userChoice > 0 && userChoice < optionsLength;
        }catch (IllegalFormatConversionException e) {
            displayErrorMessage(e);
        }
        return false;
    }
    public void displayUser(User user) {
        System.out.println(user.toString());
    }

    public void displayUsers(List<User> users) {
        for(User user: users){
            displayUser(user);
        }
    }

    public void displayQuest(Quest quest) {
        System.out.println(quest.toString());
    }

    public void displayQuests(List<Quest> quests) {
        for(Quest quest: quests){
            displayQuest(quest);
        }
    }

    public void displayCard(Card card) {
        System.out.println(card.toString());
    }

    public void displayCards(List<Card> cards) {
        for(Card card: cards){
            displayCard(card);
        }
    }

    public void displayCardTransaction(Transaction ct) throws SQLException, ClassNotFoundException {
        System.out.println(ct.toString());
    }

    public void displayCardTransactions(List<Transaction> cts) throws SQLException, ClassNotFoundException {
        for(Transaction cardTransaction: cts){
            displayCardTransaction(cardTransaction);
        }
    }

    public void displayQuestTransaction(QuestTransaction qt) {
        System.out.println(qt.toString());
    }

    public void displayQuestTransaction(List<QuestTransaction> qts) {
        for(QuestTransaction questTransaction: qts){
            displayQuestTransaction(questTransaction);
        }
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public void displayErrorMessage(Exception e) {
        e.printStackTrace();
    }
}
