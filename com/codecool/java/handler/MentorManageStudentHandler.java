//import codecool.java.dao.DbstudentDAO;
//import codecool.java.handler.CookieHelper;
//import codecool.java.helper.HttpResponse;
//import codecool.java.model.Student;
//import com.google.gson.Gson;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.sql.SQLException;
//import java.util.List;
//
//public class MentorStudentHandler implements HttpHandler {
//    CookieHelper cookieHelper = new CookieHelper();
//    HttpResponse httpResponse = new HttpResponse();
//
//    @Override
//    public void handle(HttpExchange httpExchange) throws IOException {
//        URI uri = httpExchange.getRequestURI();
//        System.out.println(uri.toString());
//        String method = httpExchange.getRequestMethod();
//        String response = "";
//        if(method.equals("GET")) {
//            if(!cookieHelper.isCookiePresent(httpExchange)){
//                httpResponse.redirectToLoginPage(httpExchange);
//            }else {
//                try {
//                    Gson gson = new Gson();
//                    response = gson.toJson(getStudentList());
//                    httpResponse.sendResponse200(httpExchange, response);
//                } catch (SQLException | ClassNotFoundException e) {
//                    httpResponse.sendResponse500(httpExchange);
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        if(method.equals("POST") && (uri.toString().equals("/mentor/students/add/"))) {
//            Student jsonData = receiveStudentFromFront(httpExchange);
//            Student student = new Student(jsonData.getLogin(), jsonData.getPassword(), jsonData.getName(), jsonData.getSurname(),true);
//            try {
//                DbstudentDAO dbstudentDAO = new DbstudentDAO();
//                dbstudentDAO.save(student);
//                httpResponse.sendResponse200(httpExchange, "saved");
//            } catch (SQLException| ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private Student receiveStudentFromFront (HttpExchange httpExchange) throws IOException {
//        InputStream requestBody = httpExchange.getRequestBody();
//        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
//        BufferedReader br = new BufferedReader(isr);
//        String stringData = br.readLine();
//        Gson gson = new Gson();
//        return gson.fromJson(stringData, Student.class);
//    }
//
//    private List<Student> getStudentList() throws SQLException, ClassNotFoundException {
//        DbstudentDAO studentDAO = new DbstudentDAO();
//        List<Student> studentList = studentDAO.loadAll();
//        return studentList;
//    }
//}