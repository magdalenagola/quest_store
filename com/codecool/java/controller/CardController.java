package codecool.java.controller;

import codecool.java.dao.CardDAO;
import codecool.java.model.Card;
import com.google.gson.Gson;

import java.net.URI;

public class CardController {
    CardDAO cardDAO;

    public CardController(CardDAO cardDAO){
        this.cardDAO = cardDAO;
    }

    public Card getCardById(int cardID) {
        return (Card) cardDAO.selectCardById(cardID);
    }

    public int getCardIDFromURI(URI uri) {
        final int URI_CARD_ID = 3;
        return Integer.parseInt(uri.toString().split("/")[URI_CARD_ID]);
    }

    public String getCardsJsonList() {
        Gson gson = new Gson();
        return gson.toJson(cardDAO.loadAll());
    }
}
