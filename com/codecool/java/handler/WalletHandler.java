package codecool.java.handler;

import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class WalletHandler implements HttpHandler {
    HttpResponse httpResponse = new HttpResponse();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        final String sessionId = getSessionIdFromCookieString(httpExchange.getRequestHeaders().getFirst("Cookie"));
        if(method.equals("GET")){
            handleGET(httpExchange, sessionId);
        }
    }

    private void handleGET(HttpExchange httpExchange, String sessionId) throws IOException {
        String response = String.valueOf(getStudentCoins(sessionId));
        httpResponse.sendResponse200(httpExchange,response);

    }

    private int getStudentCoins(String sessionId){
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        return dbstudentDAO.getCoins(dbstudentDAO.findStudentBySessionId(sessionId));
    }
    private String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
    }
}
