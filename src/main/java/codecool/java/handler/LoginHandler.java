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
    CookieHelper cookieHelper = new CookieHelper();
    HttpResponse httpResponse = new HttpResponse();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method  = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        if(method.equals("POST") && (uri.toString().equals("/login"))){
            handleLogin(httpExchange);
        }
        if (method.equals("POST") && (uri.toString().equals("/login/expired_cookie"))){
            //TODO dlaczego kiedy ciastko wygasa i następuje przekierowanie ciastko jest odnawiane?
            cookieHelper.refreshCookie(httpExchange);
            httpResponse.sendResponse200(httpExchange, "");
        }
    }

    private void handleLogin(HttpExchange httpExchange) throws IOException {
        try {
            tryLogin(httpExchange);
        } catch (NotInDatabaseException e) {
            httpResponse.sendResponse404(httpExchange);
        }
    }

    private void tryLogin(HttpExchange httpExchange) throws IOException, NotInDatabaseException {
        User user = getUserData(httpExchange.getRequestBody());
        cookieHelper.createNewCookie(httpExchange, user);
        httpResponse.sendResponse200(httpExchange, user.getClass().getSimpleName());
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
