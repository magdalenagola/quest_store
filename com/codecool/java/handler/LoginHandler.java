package codecool.java.handler;package codecool.java.controller;

import codecool.java.handler.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
        public void init() throws IOException {
            int port = 3001;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/login", new LoginHandler());
            server.createContext("/cards", new CardHandler());
            server.createContext("/coins", new WalletHandler());
            server.createContext("/static", new StaticHandler());
            server.createContext("/student/transactions", new TransactionsHandler());
            server.setExecutor(null);
            server.start();
        }

}


import codecool.java.controller.LoginController;
import codecool.java.dao.DbAuthorizationDAO;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.helper.HttpResponse;
import codecool.java.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;


public class LoginHandler implements HttpHandler {
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method  = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        if(method.equals("POST") && (uri.toString().equals("/login"))){
            try {
                User user = getUserData(httpExchange.getRequestBody());
                cookieHelper.createNewCookie(httpExchange, user);
                httpResponse.sendResponse200(httpExchange, user.getClass().getSimpleName());
            } catch (SQLException | ClassNotFoundException | ParseException e) {
                httpResponse.sendResponse500(httpExchange);
                e.printStackTrace();
            } catch (NotInDatabaseException e) {
                httpResponse.sendResponse404(httpExchange);
            }
        }
        if (method.equals("POST") && (uri.toString().equals("/login/expired_cookie"))){
            String sessionId = getSessionIdFromCookie(httpExchange.getRequestBody());
            try {
                DbAuthorizationDAO authorizationDAO = new DbAuthorizationDAO();
                authorizationDAO.disableCookie(sessionId);
                httpResponse.sendResponse200(httpExchange, "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
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

    private String getSessionIdFromCookie(InputStream requestBody) throws IOException {
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String cookie = br.readLine();
        cookie = cookie.split("=")[1].replaceAll("\"", "");
        return cookie;
    }
}
