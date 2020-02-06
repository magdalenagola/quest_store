package codecool.java.controller;

public class StudentController {
    public void run() {
        String[] options = {"Show all cards", "Show all quests", "Show my transactions", "Buy a card"};
        View.displayOptions(options);
        int userInput = View.getOptionInput(options.length);
        switch(userInput){
            case 1:
                showAllCards();
                break;
            case 2:
                showAllQuests();
                break;
            case 3:
                showTransactions();
                break;
            case 4:
                buyCard(Card card); //TODO
                break;
        }
    }

    private void showAllCards() {

    }

    private void showAllQuests() {

    }

    private void showTransactions() {

    }

    private void buyCard(Card card) {

    }
}
