package codecool.java.model;

import codecool.java.dao.DbCardDAO;

import java.sql.SQLException;
import java.sql.Date;

public class CardTransaction extends Transaction {
    private int cardId;
    private int userId;
    private Date transactionDate;
    private int cost;

    public CardTransaction(int cardId, int userId, Date transactionDate, int cost) {
        super(cardId, userId, transactionDate, cost);
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

    public String toString()  {
        String cardTransactionInfo = "";
        try {
            DbCardDAO dbCardDao = new DbCardDAO();
            Card card = dbCardDao.selectCardById(this.cardId);
            cardTransactionInfo = "Id: " + this.cardId + ", name: " + card.getTitle();
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return cardTransactionInfo;
    }
}
