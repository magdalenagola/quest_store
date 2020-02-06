package codecool.java.View;

import java.util.ArrayList;

public interface Display {
    void displayOptions(String[]);
    int getOptionInput(int maxOptionsNumber);
    void displayUser(User user);
    void displayUsers(ArrayList<User> users);
    void displayQuest(Quest quest);
    void displayQuests(ArrayList<Quest> quests);
    void displayCard(Card card);
    void displayCards(ArrayList<Card> card);
    void displayCardTransaction(CardTransaction ct);
    void displayCardTransactions(ArrayList<CardTransaction> cts);
    void displayQuestTransaction(QuestTransaction qt);
    void displayQuestTransaction(ArrayList<QuestTransaction> qts);
    void displayMessage(String msg);
    void displayErrorMessage(Exception e);
}
