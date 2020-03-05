package codecool.java.handler;

import codecool.java.dao.DbCardDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.sql.SQLException;

public class InventoryHandler implements HttpHandler {
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
                try {
                    response = getStudentCards(httpExchange);
                    httpResponse.sendResponse200(httpExchange, response);
                } catch (ClassNotFoundException | SQLException e) {
                    httpResponse.sendResponse500(httpExchange);
                    e.printStackTrace();
                }
            }
        }

    }

    private String getStudentCards(HttpExchange httpExchange) throws SQLException, ClassNotFoundException {
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
