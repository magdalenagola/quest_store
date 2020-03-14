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
        System.out.println(uri.toString());
        String method = httpExchange.getRequestMethod();
        String response = "";
        if(method.equals("GET")) {
            if(!cookieHelper.isCookiePresent(httpExchange)){
                httpResponse.redirectToLoginPage(httpExchange);
            }else {
                try {
                    Gson gson = new Gson();
                    response = gson.toJson(getMentorList());
                    httpResponse.sendResponse200(httpExchange, response);
                } catch (SQLException | ClassNotFoundException e) {
                    httpResponse.sendResponse500(httpExchange);
                    e.printStackTrace();
                }
            }
        }

        if(method.equals("POST") && (uri.toString().equals("/manager/mentor/add/"))) {
            Mentor jsonData = receiveMentorFromFront(httpExchange);
            Mentor mentor = new Mentor(0,jsonData.getLogin(),jsonData.getPassword(),jsonData.getName(),jsonData.getSurname(),jsonData.getPrimarySkill(),jsonData.getEarnings(),true);
            try {
                DbMentorDAO dbmentorDAO = new DbMentorDAO();
                dbmentorDAO.save(mentor);
                dbmentorDAO.saveDetails(mentor);
                httpResponse.sendResponse200(httpExchange, "saved");
            } catch (SQLException| ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(method.equals("POST") && (uri.toString().split("/")[3].equals("add")) && !(uri.toString().split("/")[4].equals(""))) {
            Mentor jsonData = receiveMentorFromFront(httpExchange);
            Mentor mentor = new Mentor(jsonData.getId(), jsonData.getLogin(),jsonData.getPassword(),jsonData.getName(),jsonData.getSurname(),jsonData.getPrimarySkill(),jsonData.getEarnings(),true);
            try {
                DbMentorDAO dbmentorDAO = new DbMentorDAO();
                dbmentorDAO.update(mentor);
                dbmentorDAO.updateDetails(mentor);
                httpResponse.sendResponse200(httpExchange, "updated");
            } catch (SQLException| ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(method.equals("POST") && (uri.toString().split("/")[3].equals("delete")) && !(uri.toString().split("/")[4].equals(""))) {
            InputStream requestBody = httpExchange.getRequestBody();
            InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String userId = br.readLine().replace("\"", "");
            try {
                DbMentorDAO dbmentorDAO = new DbMentorDAO();
                Mentor mentor = dbmentorDAO.selectMentorById(Integer.parseInt(userId));
                dbmentorDAO.disable(mentor);
                httpResponse.sendResponse200(httpExchange, "deleted");
            } catch (SQLException| ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private Mentor receiveMentorFromFront (HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String stringData = br.readLine();
        Gson gson = new Gson();
        return gson.fromJson(stringData, Mentor.class);
    }

    private List<Mentor> getMentorList() throws SQLException, ClassNotFoundException {
        DbMentorDAO mentorDAO = new DbMentorDAO();
        List<Mentor> mentorList = mentorDAO.loadAllActive();
        return mentorList;
    }
}