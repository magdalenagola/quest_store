package codecool.java.handler;

import codecool.java.dao.DbMentorDAO;
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

public class MentorStudentHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response = "";
        if(method.equals("GET")){
            if(!cookieHelper.isCookiePresent(httpExchange)){
                httpResponse.redirectToLoginPage(httpExchange);
            }else {
                try {
                    Gson gson = new Gson();
                    response = gson.toJson(getStudentList());
                    httpResponse.sendResponse200(httpExchange, response);
                } catch (SQLException | ClassNotFoundException e) {
                    httpResponse.sendResponse500(httpExchange);
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Student> getStudentList() throws SQLException, ClassNotFoundException {
        DbstudentDAO studentDAO = new DbstudentDAO();
        List<Student> studentList = studentDAO.loadAll();
        return studentList;
    }
}
