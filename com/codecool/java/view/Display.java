package codecool.java.view;

import codecool.java.model.Card;
import codecool.java.model.User;

import java.util.List;

public interface Display {
    public void displayOptions(String[] options);
    public int getOptionInput(int maxOptionsNumber);
    public void displayUser(User user);
    public void displayUsers(List<User> users);
    public void displayQuest(Quest quest);
    public void displayQuests(List<Quest> quests);
    public void displayCard(Card card);
    public void displayCards(List<Card> card);
    public void displayCardTransaction(CardTransaction ct);
    public void displayCardTransactions(List<Transactions> cts);
    public void displayQuestTransaction(QuestTransaction qt);
    public void displayQuestTransaction(List<QuestTransaction> qts);
    public void displayMessage(String msg);
    public void displayErrorMessage(Exception e);
}
