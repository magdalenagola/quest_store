package codecool.java.handler;

import codecool.java.dao.*;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class InventoryHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();
    CardDAO cardDAO;
    StudentDAO studentDAO;

    public InventoryHandler(StudentDAO studentDAO, CardDAO cardDAO) {
        this.studentDAO = studentDAO;
        this.cardDAO = cardDAO;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            handleGET(httpExchange);
        }
    }

    private void handleGET(HttpExchange httpExchange) throws IOException {
        String response = "";
        if (!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        } else {
            cookieHelper.refreshCookie(httpExchange);
            String sessionId = cookieHelper.getSessionId(httpExchange);
            Student student = studentDAO.findStudentBySessionId(sessionId);
            response = getStudentCards(student);
            httpResponse.sendResponse200(httpExchange, response);
        }
    }

    //TODO make private after testing
    public String getStudentCards(Student student) {
        Gson gson = new Gson();
        return gson.toJson(cardDAO.getCardsByStudent(student));
    }
}
