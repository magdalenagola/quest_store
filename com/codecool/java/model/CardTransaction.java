package codecool.java.model;

import codecool.java.dao.DbCardDao;

import java.sql.SQLException;
import java.util.Date;

public class CardTransaction extends Transaction {
    private int cardId;
    private int userId;
    private Date transactionDate;
    private int cost;

    public CardTransaction(int cardId, int userId, Date transactionDate, int cost) {
        this.cardId = cardId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.cost = cost;
    };

    public int getId() {
        return this.cardId;
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

    public String toString() throws SQLException, ClassNotFoundException {
        DbCardDao dbCardDao = new DbCardDao();
        Card card = dbCardDao.selectCardById(this.cardId);
        String cardTransactionInfo = "Id: " + this.cardId + ", name: " + card.getTitle();
        return cardTransactionInfo;
    }
}
