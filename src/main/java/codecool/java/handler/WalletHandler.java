package codecool.java.handler;

import codecool.java.controller.StudentController;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class WalletHandler implements HttpHandler {
    HttpResponse httpResponse;
    CookieHelper cookieHelper;
    StudentController studentController;
    public WalletHandler(StudentController studentController, HttpResponse httpResponse, CookieHelper cookieHelper) {
        this.studentController = studentController;
        this.httpResponse = httpResponse;
        this.cookieHelper = cookieHelper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")){
            handleGET(httpExchange);
        }
    }

    public void handleGET(HttpExchange httpExchange) throws IOException {
        Student student = getStudent(httpExchange);
        String response = String.valueOf(studentController.getStudentCoins(student));
        httpResponse.sendResponse200(httpExchange,response);

    }

    Student getStudent(HttpExchange httpExchange) {
        String sessionId = cookieHelper.getSessionId(httpExchange);
        return studentController.findStudentBySessionId(sessionId);
    }
}
