package fr.lernejo.navy_battle.web_server.api;

import fr.lernejo.navy_battle.client.NavyClient;
import fr.lernejo.navy_battle.web_server.NavyWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

public class apiTest {
    GameStartHandler testGameHandler = new GameStartHandler();
    FireHandler testFireHandler = new FireHandler();
    NavyWebServer testNavyWebServer;
    NavyClient testHttpClient;
    int testPort = 11000;

    @BeforeEach
    void setup_web_server() throws IOException {
        testNavyWebServer = new NavyWebServer(testPort);
    }
    @BeforeEach
    void setup_client() throws IOException {
        testHttpClient = new NavyClient((testPort+1), "http://localhost:" + testPort );
    }

    @AfterEach
    void close_web_servers() {
        testNavyWebServer.stop();
        testHttpClient.stop();
    }

    @AfterEach
    void increment_test_port() {
        // Leave a 20 port range for test purposes
        testPort += 20;
    }

    @Test
    void ping_assigned_path_be_start() {
        Assertions.assertEquals("/api/game/start", testGameHandler.getAssignedPath());
    }

    @Test
    void ping_assigned_path_be_fire() {
        Assertions.assertEquals("/api/game/fire", testFireHandler.getAssignedPath());
    }

    @Test
    void game_return_status_code_200_and_body_OK() {
        testNavyWebServer.createContext( testGameHandler.getAssignedPath(), testGameHandler );
        HttpResponse<String> response = testHttpClient.ping();
        Assertions.assertEquals( 200, response.statusCode() );
        Assertions.assertEquals( "OK", response.body() );
    }

    @Test
    void fire_return_status_code_200_and_body_OK() {
        testNavyWebServer.createContext( testFireHandler.getAssignedPath(), testFireHandler );
        HttpResponse<String> response = testHttpClient.ping();
        Assertions.assertEquals( 200, response.statusCode() );
        Assertions.assertEquals( "OK", response.body() );
    }

    @Test
    void sending_request_to_dead_server_not_throw_game() {
        testNavyWebServer.stop();
        Assertions.assertDoesNotThrow( () -> {
            testHttpClient.sendGETRequest( "/api/game/start" );
        });
    }

    @Test
    void sending_request_to_dead_server_should_return_null_game() {
        testNavyWebServer.stop();
        Assertions.assertNull( testHttpClient.sendGETRequest( "/api/game/start" ));
    }

    @Test
    void sending_request_to_dead_server_not_throw_fire() {
        testNavyWebServer.stop();
        Assertions.assertDoesNotThrow( () -> {
            testHttpClient.sendGETRequest( "/api/game/fire" );
        });
    }

    @Test
    void sending_request_to_dead_server_should_return_null_fire() {
        testNavyWebServer.stop();
        Assertions.assertNull( testHttpClient.sendGETRequest( "/api/game/fire" ));
    }
}
