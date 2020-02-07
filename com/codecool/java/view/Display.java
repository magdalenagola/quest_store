package codecool.java.view;

import java.util.ArrayList;

public interface Display {
    public void displayOptions(String[] options);
    public int getOptionInput(int maxOptionsNumber);
    public void displayUser(User user);
    public void displayUsers(ArrayList<User> users);
    public void displayQuest(Quest quest);
    public void displayQuests(ArrayList<Quest> quests);
    public void displayCard(Card card);
    public void displayCards(ArrayList<Card> card);
    public void displayCardTransaction(CardTransaction ct);
    public void displayCardTransactions(ArrayList<CardTransaction> cts);
    public void displayQuestTransaction(QuestTransaction qt);
    public void displayQuestTransaction(ArrayList<QuestTransaction> qts);
    public void displayMessage(String msg);
    public void displayErrorMessage(Exception e);
}
