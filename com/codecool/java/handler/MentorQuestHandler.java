package codecool.java.handler;

import codecool.java.dao.QuestDAO;
import codecool.java.helper.HttpResponse;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class MentorQuestHandler implements HttpHandler {
    CookieHelper cookieHelper;
    HttpResponse httpResponse;
    QuestDAO questDAO;

    public MentorQuestHandler(QuestDAO questDAO,CookieHelper cookieHelper, HttpResponse httpResponse){
        this.questDAO = questDAO;
        this.cookieHelper = cookieHelper;
        this.httpResponse = httpResponse;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")){
            handleGET(httpExchange);
        }
    }

    void handleGET(HttpExchange httpExchange) throws IOException {
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
        return gson.toJson(questDAO.loadAll());
    }

}
