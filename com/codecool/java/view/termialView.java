package codecool.java.view;

import java.util.IllegalFormatConversionException;
import java.util.Scanner;

public class termialView implements Display{
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

    public void displayUsers(ArrayList<User> users) {
        for(User user: users){
            displayUser(user);
        }
    }

    public void displayQuest(Quest quest) {
        System.out.println(quest.toString());
    }

    public void displayQuests(ArrayList<Quest> quests) {
        for(Quest quest: quests){
            displayQuest(quest);
        }
    }

    public void displayCard(Card card) {
        System.out.println(card.toString());
    }

    public void displayCards(ArrayList<Card> cards) {
        for(Card card: cards){
            displayCard(card);
        }
    }

    public void displayCardTransaction(CardTransaction ct) {
        System.out.println(ct.toString());
    }

    public void displayCardTransactions(ArrayList<CardTransaction> cts) {
        for(CardTransaction cardTransaction: cts){
            displayCardTransaction(cardTransaction);
        }
    }

    public void displayQuestTransaction(QuestTransaction qt) {
        System.out.println(qt.toString());
    }

    public void displayQuestTransaction(ArrayList<QuestTransaction> qts) {
        for(QuestTransaction questTransaction: qts){
            displayCardTransaction(questTransaction);
        }
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public void displayErrorMessage(Exception e) {
        e.printStackTrace();
    }
}
