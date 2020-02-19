package codecool.java.model;

import codecool.java.dao.DbQuestDAO;

import java.sql.SQLException;
import java.sql.Date;

public class QuestTransaction extends Transaction {
    private int questId;
    private int userId;
    private Date transactionDate;
    private int cost;
    private Date approvalDate;

    public QuestTransaction(int questId, int userId, Date transactionDate, int cost) {
        super(questId, userId, transactionDate, cost);
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

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String toString(){
        DbQuestDAO dbQuestDao = null;
        String questTransactionInfo = "";
        try {
            dbQuestDao = new DbQuestDAO();
            Quest quest = dbQuestDao.selectQuestById(this.questId);
            questTransactionInfo = "Id: " + this.questId + ", name: " + quest.getTitle();
            return questTransactionInfo;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return questTransactionInfo;
    }
}
