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
        String response ="";
        final String sessionId = getSessionIdFromCookieString(httpExchange.getRequestHeaders().getFirst("Cookie"));
        if(method.equals("GET")){
            try {
                response = String.valueOf(getStudentCoins(sessionId));
                httpResponse.sendResponse200(httpExchange,response);
            } catch (SQLException | ClassNotFoundException e) {
                httpResponse.sendResponse500(httpExchange);
            }
        }
    }
    private int getStudentCoins(String sessionId) throws SQLException, ClassNotFoundException {
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        return dbstudentDAO.getCoins(dbstudentDAO.findStudentBySessionId(sessionId));
    }
    private String getSessionIdFromCookieString(String cookieStr) {
        return cookieStr.split("=")[1].replace("\"","");
    }
}
