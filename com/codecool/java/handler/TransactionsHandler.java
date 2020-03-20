package codecool.java.handler;

import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbstudentDAO;
import codecool.java.dao.StudentDAO;
import codecool.java.dao.TransactionsDAO;
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
    StudentDAO studentDAO;
    TransactionsDAO transactionsDAO;

    public TransactionsHandler(StudentDAO studentDAO, TransactionsDAO transactionsDAO){
        this.studentDAO = studentDAO;
        this.transactionsDAO = transactionsDAO;
    }

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
            String sessionId = cookieHelper.getSessionId(httpExchange);
            Student student = studentDAO.findStudentBySessionId(sessionId);
            String response = getStudentTransactions(student);
            httpResponse.sendResponse200(httpExchange, response);
       }
    }

     String getStudentTransactions(Student student){
        Gson gson = new Gson();
        return gson.toJson(transactionsDAO.displayAllTransactionsByStudent(student));
    }

}
