package codecool.java.handler;

import codecool.java.dao.DbMentorDAO;
import codecool.java.dao.DbTransactionsDAO;
import codecool.java.dao.DbMentorDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Mentor;
import codecool.java.model.Transaction;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ManagerMentorsHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            handleGET(httpExchange);
        }

        if(method.equals("POST") && (uri.toString().equals("/manager/mentor/add/"))) {
            handleAddMentor(httpExchange);
        }

        if(method.equals("POST") && (uri.toString().split("/")[3].equals("add")) && !(uri.toString().split("/")[4].equals(""))) {
            handleUpdateMentor(httpExchange);
        }

        if(method.equals("POST") && (uri.toString().split("/")[3].equals("delete")) && !(uri.toString().split("/")[4].equals(""))) {
            handleDeleteMentor(httpExchange);
        }
    }

    private void handleGET(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)){
            httpResponse.redirectToLoginPage(httpExchange);
        }else {
            String response = getMentorList();
            httpResponse.sendResponse200(httpExchange, response);
        }
    }

    private void handleAddMentor(HttpExchange httpExchange) throws IOException {
        Mentor jsonData = receiveMentorFromFront(httpExchange);
        Mentor mentor = new Mentor(0,jsonData.getLogin(),jsonData.getPassword(),jsonData.getName(),jsonData.getSurname(),jsonData.getPrimarySkill(),jsonData.getEarnings(),true);
        DbMentorDAO dbmentorDAO = new DbMentorDAO();
        dbmentorDAO.save(mentor);
        dbmentorDAO.saveDetails(mentor);
        httpResponse.sendResponse200(httpExchange, "saved");
    }

    private void handleUpdateMentor(HttpExchange httpExchange) throws IOException {
        Mentor jsonData = receiveMentorFromFront(httpExchange);
        Mentor mentor = new Mentor(jsonData.getId(), jsonData.getLogin(),jsonData.getPassword(),jsonData.getName(),jsonData.getSurname(),jsonData.getPrimarySkill(),jsonData.getEarnings(),true);
        DbMentorDAO dbmentorDAO = new DbMentorDAO();
        dbmentorDAO.update(mentor);
        dbmentorDAO.updateDetails(mentor);
        httpResponse.sendResponse200(httpExchange, "updated");
    }

    private void handleDeleteMentor(HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String userId = br.readLine().replace("\"", "");
        DbMentorDAO dbmentorDAO = new DbMentorDAO();
        Mentor mentor = dbmentorDAO.selectMentorById(Integer.parseInt(userId));
        dbmentorDAO.disable(mentor);
        httpResponse.sendResponse200(httpExchange, "deleted");
    }

    private Mentor receiveMentorFromFront (HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String stringData = br.readLine();
        Gson gson = new Gson();
        return gson.fromJson(stringData, Mentor.class);
    }

    private String getMentorList(){
        DbMentorDAO mentorDAO = new DbMentorDAO();
        Gson gson = new Gson();
        return gson.toJson(mentorDAO.loadAllActive());
    }
}