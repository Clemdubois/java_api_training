package fr.lernejo.navy_battle.web_server.api;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.transactions.JSONFire;
import fr.lernejo.navy_battle.web_server.CallHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class FireHandler implements CallHandler {

    @Override
    public String getAssignedPath() {
        return "/api/game/fire";
    }

    @Override
    public String[] allowedRequestMethods() {
        return new String[]{"GET"};
    }

    @Override
    public boolean isMethodAllowed(String method) {
        return Arrays.stream(this.allowedRequestMethods()).toList().contains(method);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String body = "Not found";
        if ( ! this.isMethodAllowed( exchange.getRequestMethod() ) ) {
            exchange.sendResponseHeaders(404, body.length());
        } else {
            body = new JSONFire( JSONFire.possibilities.MISS.toString(), true).getJSONString();
            System.out.println(body);
            exchange.sendResponseHeaders(200, body.length());
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}
