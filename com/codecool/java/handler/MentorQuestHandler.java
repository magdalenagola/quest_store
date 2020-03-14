package codecool.java.handler;

import codecool.java.dao.DbQuestDAO;
import codecool.java.dao.QuestDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Quest;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorQuestHandler implements HttpHandler {
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
            String response = getQuests();
            httpResponse.sendResponse200(httpExchange, response);
        }
    }

    private String getQuests(){
        Gson gson = new Gson();
        QuestDAO questDAO = new DbQuestDAO();
        return gson.toJson(questDAO.loadAll());
    }

}
