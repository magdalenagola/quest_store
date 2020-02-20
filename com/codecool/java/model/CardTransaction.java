package codecool.java.model;

import codecool.java.dao.DbCardDAO;

import java.sql.SQLException;
import java.sql.Date;

public class CardTransaction extends Transaction {
    public CardTransaction(int cardId, int userId, Date transactionDate, int cost) {
        super(cardId, userId, transactionDate, cost);
    };

    public String toString()  {
        String cardTransactionInfo = "";
        try {
            DbCardDAO dbCardDao = new DbCardDAO();
            Card card = dbCardDao.selectCardById(getItemId());
            cardTransactionInfo = "Id: " + getItemId() + ", name: " + card.getTitle();
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return cardTransactionInfo;
    }
}
