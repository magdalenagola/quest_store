package codecool.java.handler;

import codecool.java.controller.StudentController;
import codecool.java.dao.*;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Card;
import codecool.java.model.Student;
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
    //TODO MAKE PUBLIC AFTER TESTS
    public String getCards() {
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
        Student student = studentDAO.findStudentBySessionId(sessionId);
        int studentCoins = getStudentCoins(student);
        Card card = getCardById(cardID);
        int cardPrice = card.getCost();
        if(checkCardAffordability(studentCoins, cardPrice)){
            StudentController studentController = new StudentController(new DbTransactionsDAO());
            studentController.buyCard(student,card);
            decreaseStudentCoins(student, cardPrice);
            httpResponse.sendResponse200(httpExchange,response);
        }else{
            httpResponse.sendResponse403(httpExchange);
        }
    }

    private void decreaseStudentCoins(Student student, int cardPrice){
        studentDAO.updateCoins(student,-cardPrice);
    }
    //TODO MAKE PRIVATE AFTER TESTS
    public boolean checkCardAffordability(int studentCoins, int cardPrice){
        return studentCoins >= cardPrice;
    }

    private Card getCardById(int cardID) {
        return (Card) cardDAO.selectCardById(cardID);
    }

    private int getStudentCoins(Student student){
        return studentDAO.getCoins(student);
    }
    //TODO MAKE PRIVATE AFTER TESTS
    public String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
    }
    //TODO MAKE PRIVATE AFTER TESTS
    public int getCardIDFromURI(URI uri) {
        final int URI_CARD_ID = 3;
        return Integer.parseInt(uri.toString().split("/")[URI_CARD_ID]);
    }



}
