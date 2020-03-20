package codecool.java.model;

import codecool.java.dao.DbCardDAO;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Objects;

public class CardTransaction extends Transaction {
    public CardTransaction(Item card, int userId, Date transactionDate, int cost) {
        super(card, userId, transactionDate, cost);
    };

//    public String toString()  {
//        String cardTransactionInfo = "";
//        DbCardDAO dbCardDao = new DbCardDAO();
//        Card card = dbCardDao.selectCardById(getItemId());
//        cardTransactionInfo = "Id: " + getItemId() + ", name: " + card.getTitle();
//        return cardTransactionInfo;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

}

