package codecool.java.model;

import java.util.Date;

public class CardTransaction extends Transaction {
    private int questId;
    private int userId;
    private Date transactionDate;
    int cost;

    CardTransaction(int questId, int userId, Date transactionDate, int cost) {
        this.questId = questId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.cost = cost;
    };
}
