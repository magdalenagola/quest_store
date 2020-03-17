package codecool.java.model;

import java.sql.Date;

public class QuestTransaction extends Transaction {
    private Date approvalDate;

    public QuestTransaction(Item quest, int userId, Date transactionDate, int cost) {
        super(quest, userId, transactionDate, cost);
    };

    public Date getApprovalDate() {
        return this.approvalDate;
    }
    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

//    public String toString(){
//        DbQuestDAO dbQuestDao = null;
//        String questTransactionInfo = "";
//        try {
//            dbQuestDao = new DbQuestDAO();
//            Quest quest = dbQuestDao.selectQuestById(getItemId());
//            questTransactionInfo = "Id: " + getItemId() + ", name: " + quest.getTitle();
//            return questTransactionInfo;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return questTransactionInfo;
//    }
}
