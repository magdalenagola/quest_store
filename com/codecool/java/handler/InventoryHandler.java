package codecool.java.handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class InventoryHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")){
            handleGET(httpExchange);
        }

    }

    private void handleGET(HttpExchange httpExchange) throws IOException {
        String response = "";
        if(!cookieHelper.isCookiePresent(httpExchange)){
            httpResponse.redirectToLoginPage(httpExchange);
        }else {
            cookieHelper.refreshCookie(httpExchange);
            response = getStudentCards(httpExchange);
            httpResponse.sendResponse200(httpExchange, response);

        }
    }

    private String getStudentCards(HttpExchange httpExchange){
        DbCardDAO cardDAO = new DbCardDAO();
        DbstudentDAO studentDAO = new DbstudentDAO();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        Student student = studentDAO.findStudentBySessionId(getSessionIdFromCookieString(cookieStr));
        Gson gson = new Gson();
        return gson.toJson(cardDAO.getCardsByStudent(student));
    }

    private String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
    }
}
