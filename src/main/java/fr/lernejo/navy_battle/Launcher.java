package fr.lernejo.navy_battle;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    static class MyHandler implements HttpHandler {

        private static final String RESPONSE = "Hello world !";

        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, RESPONSE.length());
            OutputStream os = t.getResponseBody();
            os.write(RESPONSE.getBytes());
            os.close();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        server.createContext("/ping", new MyHandler());

        server.setExecutor(Executors.newFixedThreadPool(1)); // creates a default executor
        server.start();
    }
}
