package codecool.java.model;

import java.util.Date;

public class QuestTransaction extends Transaction {
    private int questId;
    private int userId;
    private Date transactionDate;
    int cost;

    QuestTransaction(int questId, int userId, Date transactionDate, int cost) {
        this.questId = questId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.cost = cost;
    };
}
