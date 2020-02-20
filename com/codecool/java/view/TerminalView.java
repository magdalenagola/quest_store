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
            return userChoice > 0 && userChoice <= optionsLength;
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

    public void displayUsersWithIndexes(List<User> users) {
        for(int i = 0; i < users.size(); i++){
            System.out.println(String.format("(%d) %s", i+1, users.get(i)));
        }
    }

    public void displayQuest(Quest quest) {
        System.out.println(quest.toString());
    }

    public void displayQuests(List<Quest> quests) {
        for(int i = 0; i < quests.size(); i++){
            System.out.println(String.format("(%d) %s", i+1, quests.get(i)));
        }
    }

    public void displayCard(Card card) {
        System.out.println(card.toString());
    }

    public void displayCards(List<Card> cards) {
        for(int i = 0; i < cards.size(); i++){
            System.out.println(String.format("(%d) %s", i+1, cards.get(i)));
        }
    }

    public void displayCardTransaction(Transaction ct) {
        System.out.println(ct.toString());
    }

    public void displayCardTransactions(List<Transaction> cts) {
        for(int i = 0; i < cts.size(); i++){
            System.out.println(String.format("(%d) %s", i+1, cts.get(i)));
        }
    }

    public void displayQuestTransaction(QuestTransaction qt) {
        System.out.println(qt.toString());
    }

    public void displayQuestTransaction(List<QuestTransaction> qts) {
        for(int i = 0; i < qts.size(); i++){
            System.out.println(String.format("(%d) %s", i+1, qts.get(i)));
        }
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public void displayErrorMessage(Exception e) {
        e.printStackTrace();
    }
}
