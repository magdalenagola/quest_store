package codecool.java.handler;

import codecool.java.controller.LoginController;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URI;
import java.sql.SQLException;


public class LoginHandler implements HttpHandler {
    
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method  = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        if(method.equals("POST") && (uri.equals("/login"))){
            try {
                User user = getUserData(httpExchange.getRequestBody());
                httpExchange.getResponseHeaders().set("Location","/cards");
                sendResponse200(httpExchange, "OK");
            } catch (SQLException | ClassNotFoundException e) {
                sendResponse500(httpExchange);
            } catch (NotInDatabaseException e) {
                sendResponse404(httpExchange);
            }
        }
    }

    private User getUserData(InputStream requestBody) throws IOException, SQLException, ClassNotFoundException, NotInDatabaseException {
        LoginController loginController = new LoginController();
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String loginData = br.readLine();
        loginData = loginData.substring(1, loginData.length()-1);
        String[] loginPassword = loginData.split(",");
        loginPassword[0] = loginPassword[0].replaceAll("\"", "");
        loginPassword[1] = loginPassword[1].replaceAll("\"", "");
        User user = loginController.authenticate(loginPassword[0], loginPassword[1]);
        System.out.println(user.toString());
        return user;

    }

    private void sendResponse200(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private void sendResponse303(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(303,0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
    private void sendResponse404(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(404, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
    private void sendResponse500(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(500,0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
}
