package codecool.java.handler;

import codecool.java.dao.CardDAO;
import codecool.java.dao.DbCardDAO;
import codecool.java.model.Card;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response="";
        if(method.equals("GET")){
            response = getCards();
        }
        sendResponse200(httpExchange,response);
    }

    private String getCards() {
        List<Card> cards = new ArrayList<>();
        Gson gson = new Gson();
        try {
            CardDAO cardDAO = new DbCardDAO();
            cards = cardDAO.loadAll();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gson.toJson(cards);
    }
    private void sendResponse200(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
