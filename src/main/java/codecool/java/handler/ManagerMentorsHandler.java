package codecool.java.handler;

import codecool.java.dao.DbMentorDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Mentor;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.List;

public class ManagerMentorsHandler implements HttpHandler {
    private DbMentorDAO dbMentorDAO = new DbMentorDAO();
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!cookieHelper.isCookiePresent(httpExchange)) {
            httpResponse.redirectToLoginPage(httpExchange);
        } else {
            URI uri = httpExchange.getRequestURI();
            String method = httpExchange.getRequestMethod();
            switch (method) {
                case "GET":
                    handleGET(httpExchange);
                    break;
                case "POST":
                    handlePOST(httpExchange, uri);
                    break;
            }
        }
    }

    public void handleGET(HttpExchange httpExchange) throws IOException {
        String response = getMentorList();
        httpResponse.sendResponse200(httpExchange, response);
    }

    public String handlePOST(HttpExchange httpExchange, URI uri) throws IOException {
        if ("uri.toString().equals(\"/manager/mentor/add/\"))".equals(uri)) {
            HttpResponse httpResponse = handleAddMentor(httpExchange);
            httpResponse.sendResponse200();
            return "";
        }

        if ((uri.toString().split("/")[3].equals("add")) && !(uri.toString().split("/")[4].equals(""))) {
            HttpResponse httpResponse = handleUpdateMentor(httpExchange);
            httpResponse.sendResponse200();
        }

        if ((uri.toString().split("/")[3].equals("delete")) && !(uri.toString().split("/")[4].equals(""))) {
            HttpResponse httpResponse = handleDeleteMentor(httpExchange);
            httpResponse.sendResponse200();
        }

        return "No response";
    }

    private HttpResponse handleAddMentor(HttpExchange httpExchange) throws IOException {
        Mentor mentor = receiveMentorFromFront(httpExchange);
        saveMentor(mentor);
        return new HttpResponse(httpExchange, "saved");
    }

    private void saveMentor(Mentor mentor) {
        dbMentorDAO.save(mentor);
    }

    public HttpResponse handleUpdateMentor(HttpExchange httpExchange) throws IOException {
        Mentor mentor = receiveMentorFromFront(httpExchange);
        updateMentor(mentor);
        return new HttpResponse(httpExchange, "updated");
    }

    private void updateMentor(Mentor mentor) {
        dbMentorDAO.update(mentor);
    }

    private HttpResponse handleDeleteMentor(HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String userId = br.readLine().replace("\"", "");
        selectMentor(userId);
        return new HttpResponse(httpExchange, "deleted");
    }

    private void selectMentor(String userId) {
        Mentor mentor = dbMentorDAO.selectMentorById(Integer.parseInt(userId));
        deleteMentor(mentor);
    }

    private void deleteMentor(Mentor mentor) {
        dbMentorDAO.disable(mentor);
    }

    private Mentor receiveMentorFromFront(HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String stringData = br.readLine();
        Gson gson = new Gson();
        return gson.fromJson(stringData, Mentor.class);
    }

    private String getMentorList() {
        List<Mentor> mentors = dbMentorDAO.loadAllActive();
        return getMentorsJson(mentors);
    }

    public String getMentorsJson(List<Mentor> mentors) {
        Gson gson = new Gson();
        return gson.toJson(mentors);
    }
}