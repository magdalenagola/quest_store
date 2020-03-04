package codecool.java.handler;

import codecool.java.dao.DbMentorDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Mentor;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

public class ManagerMentorsHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            if(!cookieHelper.isCookiePresent(httpExchange)){
                httpResponse.redirectToLoginPage(httpExchange);
            }else {
                tryHandleGet(httpExchange);
            }
        }

    }

    private void tryHandleGet(HttpExchange httpExchange) throws IOException {
        String response;
        try {
            Gson gson = new Gson();
            response = gson.toJson(getMentorsList());
            httpResponse.sendResponse200(httpExchange, response);
        } catch (SQLException | ClassNotFoundException e) {
            httpResponse.sendResponse500(httpExchange);
            e.printStackTrace();
        }
    }

    private List<Mentor> getMentorsList() throws SQLException, ClassNotFoundException {
        DbMentorDAO mentorDAO = new DbMentorDAO();
        return mentorDAO.loadAll();
    }
}
