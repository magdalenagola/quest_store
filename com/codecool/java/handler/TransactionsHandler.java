package codecool.java.handler;

import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import codecool.java.model.Transaction;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TransactionsHandler implements HttpHandler {
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
        if(!cookieHelper.isCookiePresent(httpExchange)){
            httpResponse.redirectToLoginPage(httpExchange);
        }else {
            cookieHelper.refreshCookie(httpExchange);
            String response = getStudentTransactions(httpExchange);
            httpResponse.sendResponse200(httpExchange, response);

       }
    }

    private String getStudentTransactions(HttpExchange httpExchange){
        DbstudentDAO studentDAO = new DbstudentDAO();
        Gson gson = new Gson();
        DbTransactionsDAO transactionsDAO = new DbTransactionsDAO();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        Student student = studentDAO.findStudentBySessionId(getSessionIdFromCookieString(cookieStr));
        return gson.toJson(transactionsDAO.displayAllTransactionsByStudent(student));
    }
    //TODO make private after testing
    public String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
    }
}
