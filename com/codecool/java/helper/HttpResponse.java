package codecool.java.helper;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private HttpExchange httpExchange;
    private String response;

    public HttpResponse(HttpExchange httpExchange, String response) {
        this.httpExchange = httpExchange;
        this.response = response;
    }

    public HttpResponse() {
    }

    public void sendResponse200(HttpExchange httpExchange, String response) throws IOException {
        prepareResponse200(httpExchange, response);
    }

    public void sendResponse200() throws IOException {
        prepareResponse200(httpExchange, response);
    }

    private void prepareResponse200(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void sendResponse303(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(303,0);
        getResponseBody(httpExchange);
    }
    public void sendResponse404(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(404, 0);
        getResponseBody(httpExchange);
    }
    public void sendResponse500(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(500,0);
        getResponseBody(httpExchange);
    }
    public void redirectToLoginPage(HttpExchange httpExchange) throws IOException {
        sendResponse303(httpExchange);
    }

    public void sendResponse403(HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(403,0);
        getResponseBody(httpExchange);
    }

    private void getResponseBody(HttpExchange httpExchange) throws IOException {
        OutputStream os = httpExchange.getResponseBody();
        os.close();
    }
}
