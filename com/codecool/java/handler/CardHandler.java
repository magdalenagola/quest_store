package codecool.java.handler;

import codecool.java.controller.StudentController;
import codecool.java.dao.CardDAO;
import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Card;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CardHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response="";
        if(method.equals("GET")){
            if(!cookieHelper.isCookiePresent(httpExchange)){
                httpResponse.redirectToLoginPage(httpExchange);
            }else {
                cookieHelper.refreshCookie(httpExchange);
                response = getCards();
                httpResponse.sendResponse200(httpExchange, response);
            }
        }
        if(method.equals("POST")){
            if(!cookieHelper.isCookiePresent(httpExchange)) {
                httpResponse.redirectToLoginPage(httpExchange);
            } else {
                cookieHelper.refreshCookie(httpExchange);
                URI uri = httpExchange.getRequestURI();
                final int URI_CARD_ID = 3;
                final String sessionId = getSessionIdFromCookieString(httpExchange.getRequestHeaders().getFirst("Cookie"));
                int cardID = Integer.parseInt(uri.toString().split("/")[URI_CARD_ID]);
                try {
                    DbstudentDAO dbstudentDAO = new DbstudentDAO();
                    int studentCoins = getStudentCoins(sessionId);
                    int studentID = dbstudentDAO.findStudentBySessionId(sessionId).getId();
                    StudentController studentController = new StudentController();
                    if(checkCardAffordability(studentCoins,cardID)){
                        studentController.buyCard(studentID,cardID);
                        decreaseStudentCoins(sessionId, cardID, dbstudentDAO);
                        httpResponse.sendResponse200(httpExchange,response);
                    }else{
                        httpResponse.sendResponse403(httpExchange);
                    }
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    httpResponse.sendResponse500(httpExchange);
                }
            }
        }

    }

    private void decreaseStudentCoins(String sessionId, int cardID, DbstudentDAO dbstudentDAO) throws SQLException, ClassNotFoundException {
        CardDAO cardDAO = new DbCardDAO();
        Card card = (Card) cardDAO.selectCardById(cardID);
        dbstudentDAO.updateCoins(dbstudentDAO.findStudentBySessionId(sessionId),-card.getCost());
    }

    private boolean checkCardAffordability(int studenCoins, int cardID) throws SQLException, ClassNotFoundException {
        CardDAO cardDAO = new DbCardDAO();
        Card card = (Card) cardDAO.selectCardById(cardID);
        return studenCoins >= card.getCost();
    }
    private int getStudentCoins(String sessionId) throws SQLException, ClassNotFoundException {
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        return dbstudentDAO.getCoins(dbstudentDAO.findStudentBySessionId(sessionId));
    }

    private String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
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

}
