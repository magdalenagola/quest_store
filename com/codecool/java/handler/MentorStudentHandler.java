package codecool.java.handler;

import codecool.java.dao.DbstudentDAO;
import codecool.java.helper.HttpResponse;
import codecool.java.model.Student;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;

public class MentorStudentHandler implements HttpHandler {
    CookieHelper cookieHelper;
    HttpResponse httpResponse;

    public MentorStudentHandler(CookieHelper cookieHelper, HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        this.cookieHelper = cookieHelper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            handleGET(httpExchange);
        }

        if(method.equals("POST") && (uri.toString().equals("/mentor/students/add/"))) {
            handleAddStudent(httpExchange);
        }

        if(method.equals("POST") && (uri.toString().split("/")[3].equals("add")) && !(uri.toString().split("/")[4].equals(""))) {
            handleUpdateStudent(httpExchange);
        }

        if(method.equals("POST") && (uri.toString().split("/")[3].equals("delete")) && !(uri.toString().split("/")[4].equals(""))) {
            handleDeleteStudent(httpExchange);
        }
    }

    public void handleGET(HttpExchange httpExchange) throws IOException {
        if(!cookieHelper.isCookiePresent(httpExchange)){
            httpResponse.redirectToLoginPage(httpExchange);
        }else {
            String response = getStudentList();
            httpResponse.sendResponse200(httpExchange, response);
        }
    }

    public String getStudentList(){
        DbstudentDAO studentDAO = new DbstudentDAO();
        Gson gson = new Gson();
        return gson.toJson(studentDAO.loadAllActive());
    }

    public void handleAddStudent(HttpExchange httpExchange) throws IOException {
        Student jsonData = receiveStudentFromFront(httpExchange);
        Student student = new Student(jsonData.getLogin(), jsonData.getPassword(), jsonData.getName(), jsonData.getSurname(),true);
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        dbstudentDAO.save(student);
        httpResponse.sendResponse200(httpExchange, "saved");
    }

    public void handleUpdateStudent(HttpExchange httpExchange) throws IOException {
        Student jsonData = receiveStudentFromFront(httpExchange);
        Student student = new Student(jsonData.getId(), jsonData.getLogin(), jsonData.getPassword(), jsonData.getName(), jsonData.getSurname(),true);
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        dbstudentDAO.update(student);
        httpResponse.sendResponse200(httpExchange, "updated");
    }

    public void handleDeleteStudent(HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String userId = br.readLine().replace("\"", "");
        DbstudentDAO dbstudentDAO = new DbstudentDAO();
        Student student = dbstudentDAO.selectStudentById(Integer.parseInt(userId));
        dbstudentDAO.disable(student);
        httpResponse.sendResponse200(httpExchange, "deleted");
    }

    public Student receiveStudentFromFront (HttpExchange httpExchange) throws IOException {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String stringData = br.readLine();
        Gson gson = new Gson();
        return gson.fromJson(stringData, Student.class);
    }
}