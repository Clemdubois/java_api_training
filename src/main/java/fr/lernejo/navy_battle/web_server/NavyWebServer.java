package fr.lernejo.navy_battle.web_server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.web_server.api.FireHandler;
import fr.lernejo.navy_battle.web_server.api.GameStartHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NavyWebServer {

    protected final int portChoisi;
    private final HttpServer myHttpServer;
    private final List<CallHandler> contexts = new ArrayList<>();

    public NavyWebServer(int port) throws IOException {
        this.portChoisi = port;
        this.myHttpServer = HttpServer.create( new InetSocketAddress( this.portChoisi ), 0);
        this.myHttpServer.setExecutor( Executors.newFixedThreadPool(1) );
        this.contexts.add(new PingHandler());
        this.contexts.add(new GameStartHandler());
        this.contexts.add(new FireHandler());
        this.setupContexts();
        this.myHttpServer.start();
    }

    public void createContext(String path, CallHandler handler) {
        this.myHttpServer.createContext(path, handler);
    }

    public void setupContexts() {
        contexts.forEach(
            context -> this.createContext(context.getAssignedPath(), context)
        );
    }

    public void stop() {
        this.myHttpServer.stop(0);
    }

}
