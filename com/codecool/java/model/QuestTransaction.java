package codecool.java.model;

import codecool.java.dao.DbQuestDAO;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QuestTransaction that = (QuestTransaction) o;
        return Objects.equals(approvalDate, that.approvalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), approvalDate);
    }
}
