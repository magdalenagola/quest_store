package codecool.java.handler;

import codecool.java.controller.CardController;
import codecool.java.controller.StudentController;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Card;
import codecool.java.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URI;


public class CardHandler implements HttpHandler {

    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();
    CardController cardController;
    StudentController studentController;

    public CardHandler(CardController cardController, StudentController studentController){
        this.cardController = cardController;
        this.studentController = studentController;
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
        String response = cardController.getCardsJsonList();
        httpResponse.sendResponse200(httpExchange, response);
    }

    private void handlePOST(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        } else {
            cookieHelper.refreshCookie(httpExchange);
            Card card = getCardFromURI(httpExchange);
            Student student = getStudentBySessionId(httpExchange);
            tryBuyCard(httpExchange, student, card);
        }
    }

    private Student getStudentBySessionId(HttpExchange httpExchange) {
        String sessionId = cookieHelper.getSessionId(httpExchange);
        return studentController.findStudentBySessionId(sessionId);
    }

    private Card getCardFromURI(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        int cardID = cardController.getCardIDFromURI(uri);
        return cardController.getCardById(cardID);
    }

    private void tryBuyCard(HttpExchange httpExchange, Student student, Card card)throws IOException{
        String response = "";
        int studentCoins = studentController.getStudentCoins(student);
        if(checkCardAffordability(studentCoins, card.getCost())){
            studentController.buyCard(student,card);
            studentController.decreaseStudentCoins(student, card.getCost());
            httpResponse.sendResponse200(httpExchange,response);
        }else{
            httpResponse.sendResponse403(httpExchange);
        }
    }

    //TODO MAKE PRIVATE AFTER TESTS
    public boolean checkCardAffordability(int studentCoins, int cardPrice){
        return studentCoins >= cardPrice;
    }
}
