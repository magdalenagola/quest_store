package codecool.java.model;

import java.util.Date;

public class CardTransaction extends Transaction {
    private int cardId;
    private int userId;
    private Date transactionDate;
    int cost;

    CardTransaction(int cardId, int userId, Date transactionDate, int cost) {
        this.cardId = cardId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.cost = cost;
    };
}
