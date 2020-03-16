package codecool.java.handler;

import codecool.java.controller.StudentController;
import codecool.java.dao.*;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Card;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URI;


public class CardHandler implements HttpHandler {

    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();
    StudentDAO studentDAO;
    CardDAO cardDAO;

    public CardHandler(StudentDAO studentDAO, CardDAO cardDAO){
        this.studentDAO = studentDAO;
        this.cardDAO = cardDAO;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        switch(method){
            case "GET": handleGET(httpExchange);
            case "POST" : handlePOST(httpExchange);
        }
    }

    private void handleGET(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        }
        cookieHelper.refreshCookie(httpExchange);
        String response = getCards();
        httpResponse.sendResponse200(httpExchange, response);
    }

    private String getCards() {
        Gson gson = new Gson();
        return gson.toJson(cardDAO.loadAll());
    }

    private void handlePOST(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        } else {
            cookieHelper.refreshCookie(httpExchange);
            URI uri = httpExchange.getRequestURI();
            final String sessionId = getSessionIdFromCookieString(httpExchange.getRequestHeaders().getFirst("Cookie"));
            int cardID = getCardIDFromURI(uri);
            tryBuyCard(httpExchange, sessionId, cardID);
        }
    }

    private void tryBuyCard(HttpExchange httpExchange, String sessionId, int cardID)throws IOException{
        String response = "";
        int studentCoins = getStudentCoins(sessionId);
        int studentID = studentDAO.findStudentBySessionId(sessionId).getId();
        StudentController studentController = new StudentController(new DbCardDAO(), new DbTransactionsDAO());
        Card card = getCardById(cardID);
        if(checkCardAffordability(studentCoins,card)){
            studentController.buyCard(studentID,cardID);
            decreaseStudentCoins(sessionId, card);
            httpResponse.sendResponse200(httpExchange,response);
        }else{
            httpResponse.sendResponse403(httpExchange);
        }
    }

    private void decreaseStudentCoins(String sessionId, Card card){
        studentDAO.updateCoins(studentDAO.findStudentBySessionId(sessionId),-card.getCost());
    }
    //TODO MAKE PRIVATE AFTER TESTS
    public boolean checkCardAffordability(int studenCoins, Card card){
        return studenCoins >= card.getCost();
    }

    private Card getCardById(int cardID) {
        return (Card) cardDAO.selectCardById(cardID);
    }

    private int getStudentCoins(String sessionId){
        return studentDAO.getCoins(studentDAO.findStudentBySessionId(sessionId));
    }

    private String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
    }

    private int getCardIDFromURI(URI uri) {
        final int URI_CARD_ID = 3;
        return Integer.parseInt(uri.toString().split("/")[URI_CARD_ID]);
    }



}
