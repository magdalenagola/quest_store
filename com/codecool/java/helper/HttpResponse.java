package codecool.java.helper;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    public void sendResponse200(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    public void sendResponse303(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(303,0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
    public void sendResponse404(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(404, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
    public void sendResponse500(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(500,0);
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
    public void redirectToLoginPage(HttpExchange httpExchange) throws IOException {
//        httpExchange.getResponseHeaders().set("Location","/static/index.html");
        sendResponse303(httpExchange);
    }
}
