package codecool.java.handler;

import codecool.java.controller.LoginController;
import codecool.java.dao.DbAuthorizationDAO;
import codecool.java.dao.NotInDatabaseException;
import codecool.java.helper.HttpResponse;
import codecool.java.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URI;

public class LoginHandler implements HttpHandler {
    CookieHelper cookieHelper;
    HttpResponse httpResponse;

    public LoginHandler(CookieHelper cookieHelper, HttpResponse httpResponse){
        this.cookieHelper = cookieHelper;
        this.httpResponse = httpResponse;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method  = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        if(method.equals("POST") && (uri.toString().equals("/login"))){
            try {
                handleLogin(httpExchange, getUserData(httpExchange.getRequestBody()));
            } catch (NotInDatabaseException e) {
                e.printStackTrace();
            }
        }
        if (method.equals("POST") && (uri.toString().equals("/login/expired_cookie"))){
            //TODO dlaczego kiedy ciastko wygasa i następuje przekierowanie ciastko jest odnawiane?
            cookieHelper.refreshCookie(httpExchange);
            httpResponse.sendResponse200(httpExchange, "");
        }
    }

    void handleLogin(HttpExchange httpExchange, User user) throws IOException {
        try {
            tryLogin(httpExchange, user);
        } catch (NotInDatabaseException e) {
            httpResponse.sendResponse404(httpExchange);
        }
    }

     void tryLogin(HttpExchange httpExchange, User user) throws IOException, NotInDatabaseException {
        cookieHelper.createNewCookie(httpExchange, user);
        httpResponse.sendResponse200(httpExchange, getUserTypeName(user));
    }

     String getUserTypeName(User user){
        return user.getClass().getSimpleName();
    }

        User getUserData(InputStream requestBody) throws IOException, NotInDatabaseException {
        LoginController loginController = new LoginController(new DbAuthorizationDAO());
        InputStreamReader isr = new InputStreamReader(requestBody, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String loginData = br.readLine();
        loginData = loginData.substring(1, loginData.length()-1);
        String[] loginPassword = loginData.split(",");
        loginPassword[0] = loginPassword[0].replaceAll("\"", "");
        loginPassword[1] = loginPassword[1].replaceAll("\"", "");
        User user = loginController.authenticate(loginPassword[0], loginPassword[1]);
        return user;

    }
}
