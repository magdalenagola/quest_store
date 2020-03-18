package codecool.java.handler;

import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class WalletHandler implements HttpHandler {
    HttpResponse httpResponse;
    CookieHelper cookieHelper;

    public WalletHandler(HttpResponse httpResponse, CookieHelper cookieHelper) {
        this.httpResponse = httpResponse;
        this.cookieHelper = cookieHelper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String sessionId = cookieHelper.getSessionId(httpExchange);
        if(method.equals("GET")){
            handleGET(httpExchange, sessionId);
        }
    }

    public void handleGET(HttpExchange httpExchange, String sessionId) throws IOException {
        String response = String.valueOf(getStudentCoins(sessionId));
        httpResponse.sendResponse200(httpExchange,response);

    }

    public int getStudentCoins(String sessionId){
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        return dbstudentDAO.getCoins(dbstudentDAO.findStudentBySessionId(sessionId));
    }
}
