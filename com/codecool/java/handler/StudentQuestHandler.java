package codecool.java.handler;

import codecool.java.dao.DbQuestDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Quest;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentQuestHandler implements HttpHandler {
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
                cookieHelper.refreshCookie(httpExchange);
                response = getQuests(httpExchange);
                httpResponse.sendResponse200(httpExchange, response);
            }
        }
    }

    private String getQuests(HttpExchange httpExchange) throws IOException {
        List<Quest> quests = new ArrayList<>();
        Gson gson = new Gson();
        try {
            DbQuestDAO questDAO = new DbQuestDAO();
            quests = questDAO.loadAllActive();
        } catch (SQLException e) {
            httpResponse.sendResponse500(httpExchange);
        }
        return gson.toJson(quests);
    }

}
