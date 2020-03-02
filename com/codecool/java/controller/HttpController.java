package codecool.java.controller;

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
