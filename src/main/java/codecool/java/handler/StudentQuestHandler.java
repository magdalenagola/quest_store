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

public class StudentQuestHandler implements HttpHandler {
    CookieHelper cookieHelper;
    HttpResponse httpResponse;
    QuestDAO questDAO;

    public StudentQuestHandler(QuestDAO questDAO, CookieHelper cookieHelper, HttpResponse httpResponse) {
        this.cookieHelper = cookieHelper;
        this.httpResponse = httpResponse;
        this.questDAO = questDAO;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            handleGET(httpExchange);
        }
    }

    public void handleGET(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)){
            httpResponse.redirectToLoginPage(httpExchange);
        }else {
            cookieHelper.refreshCookie(httpExchange);
            String response = getQuests();
            httpResponse.sendResponse200(httpExchange, response);
        }
    }

    public String getQuests() {
        Gson gson = new Gson();
        return gson.toJson(questDAO.loadAllActive());
    }

}
