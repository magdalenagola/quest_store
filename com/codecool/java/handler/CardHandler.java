package codecool.java.handler;

import codecool.java.controller.CardController;
import codecool.java.controller.StudentController;
import codecool.java.helper.HttpResponse;
import codecool.java.model.CannotAffordCardException;
import codecool.java.model.Card;
import codecool.java.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.Cookie;

import java.io.IOException;
import java.net.URI;


public class CardHandler implements HttpHandler {

    CookieHelper cookieHelper;
    HttpResponse httpResponse;
    CardController cardController;
    StudentController studentController;

    public CardHandler(CardController cardController, StudentController studentController, CookieHelper cookieHelper, HttpResponse httpResponse){
        this.cardController = cardController;
        this.studentController = studentController;
        this.cookieHelper = cookieHelper;
        this.httpResponse = httpResponse;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        switch(method){
            case "GET": handleGET(httpExchange);
            case "POST" : handlePOST(httpExchange);
        }
    }

    void handleGET(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        }
        cookieHelper.refreshCookie(httpExchange);
        String response = cardController.getCardsJsonList();
        httpResponse.sendResponse200(httpExchange, response);
    }

    void handlePOST(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        } else {
            cookieHelper.refreshCookie(httpExchange);
            Card card = getCardFromURI(httpExchange);
            Student student = getStudentBySessionId(httpExchange);
            try {
                tryBuyCard(student, card);
                httpResponse.sendResponse200(httpExchange,"Card bought");
            }catch(CannotAffordCardException e){
                httpResponse.sendResponse403(httpExchange);
            }
        }
    }

    Student getStudentBySessionId(HttpExchange httpExchange) {
        String sessionId = cookieHelper.getSessionId(httpExchange);
        return studentController.findStudentBySessionId(sessionId);
    }

    Card getCardFromURI(HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        int cardID = cardController.getCardIDFromURI(uri);
        return cardController.getCardById(cardID);
    }

    void tryBuyCard(Student student, Card card)throws CannotAffordCardException {
        int studentCoins = studentController.getStudentCoins(student);
        if(checkCardAffordability(studentCoins, card.getCost())){
            manageSuccessfulTransaction(student, card);
        }else{
            throw new CannotAffordCardException();
        }
    }

    private void manageSuccessfulTransaction(Student student, Card card) {
        studentController.buyCard(student,card);
        studentController.decreaseStudentCoins(student, card.getCost());
    }


    boolean checkCardAffordability(int studentCoins, int cardPrice){
        return studentCoins >= cardPrice;
    }
}
