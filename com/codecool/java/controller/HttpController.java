package codecool.java.controller;

import codecool.java.dao.*;
import codecool.java.handler.*;
import codecool.java.helper.HttpResponse;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
        public void init() throws IOException {
            int port = 3001;
            CookieHelper cookieHelper = new CookieHelper();
            HttpResponse httpResponse = new HttpResponse();

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/login", new LoginHandler());
            server.createContext("/cards",
            new CardHandler(new CardController(new DbCardDAO()),
            new StudentController(new DbstudentDAO(),new DbTransactionsDAO()),
            cookieHelper, httpResponse));
            server.createContext("/coins", new WalletHandler(httpResponse));
            server.createContext("/mentor/students", new MentorStudentHandler(cookieHelper, httpResponse));
            server.createContext("/static", new StaticHandler());
            server.createContext("/student/transactions", new TransactionsHandler(new DbstudentDAO(), new DbTransactionsDAO()));
            server.createContext("/student/inventory", new InventoryHandler(new DbstudentDAO(), new DbCardDAO()));
            server.createContext("/mentor/quests", new MentorQuestHandler(new DbQuestDAO(), cookieHelper, httpResponse));
            server.createContext("/student/quests", new StudentQuestHandler());
            server.createContext("/manager/mentor", new ManagerMentorsHandler());
            server.setExecutor(null);
            server.start();
        }

}
