package codecool.java.model;

import java.util.Date;

public class QuestTransaction extends Transaction {
    private int questId;
    private int userId;
    private Date transactionDate;
    private int cost;
    private Date approvalDate;

    QuestTransaction(int questId, int userId, Date transactionDate, int cost) {
        this.questId = questId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.cost = cost;
    };

    public int getId() {
        return this.questId;
    }

    public int getUserId() {
        return this.userId;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public int getCost() {
        return this.cost;
    }

    public Date getApprovalDate() {
        return this.approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }
}
