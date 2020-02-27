package codecool.java.handler;

import codecool.java.controller.StudentController;
import codecool.java.dao.CardDAO;
import codecool.java.dao.DbCardDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Card;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.Cookie;

import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            }else{
                response = getCards();
                httpResponse.sendResponse200(httpExchange,response);
            }
        }
        if(method.equals("POST")){
            if(!cookieHelper.isCookiePresent(httpExchange)){
                httpResponse.redirectToLoginPage(httpExchange);
            }else{
                URI uri = httpExchange.getRequestURI();
                final int URI_CARD_ID = 3;
                int cardID = Integer.parseInt(uri.toString().split("/")[URI_CARD_ID]);
                Optional<HttpCookie> cookie = cookieHelper.getSessionIdCookie(httpExchange);
                try {
                    int studentID = Integer.parseInt(cookie.get().getValue());
                    StudentController studentController = new StudentController();
                    studentController.buyCard(studentID,cardID);
                    httpResponse.sendResponse200(httpExchange,response);
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    httpResponse.sendResponse500(httpExchange);
                }
            }
        }

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
