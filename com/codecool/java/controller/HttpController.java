package codecool.java.controller;

import codecool.java.handler.LoginHandler;
import codecool.java.handler.StaticHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
        public void init() throws IOException {
            int port = 8000;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/login", new LoginHandler());
            server.createContext("/static", new StaticHandler());
            server.setExecutor(null);
            server.start();
        }
}
