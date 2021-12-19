package fr.lernejo.navy_battle.web_server;

import fr.lernejo.navy_battle.client.NavyClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

class WebServerTest {

    PingHandler testPingHandler = new PingHandler();
    NavyWebServer testNavyWebServer;
    NavyClient testHttpClient;
    int testPort = 8000;

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
    void ping_assigned_path_be_ping() {
        Assertions.assertEquals("/ping", testPingHandler.getAssignedPath());
    }

    @Test
    void ping_return_status_code_200_and_body_OK() {
        testNavyWebServer.createContext( testPingHandler.getAssignedPath(), testPingHandler );
        HttpResponse<String> response = testHttpClient.ping();
        Assertions.assertEquals( 200, response.statusCode() );
        Assertions.assertEquals( "OK", response.body() );
    }

    @Test
    void setupContexts_setup_all_contexts() {
        testNavyWebServer.setupContexts();
        HttpResponse<String> response = testHttpClient.ping();
        Assertions.assertEquals( 200, response.statusCode() );
        Assertions.assertEquals( "OK", response.body() );
        //Add content to check that all contexts are set
    }

    @Test
    void unknown_context_return_status_code_404() {
        testNavyWebServer.setupContexts();
        HttpResponse<String> response = testHttpClient.sendGETRequest( "/unknownPath" );
        Assertions.assertEquals( 404, response.statusCode() );
    }

    @Test
    void sending_request_to_dead_server_not_throw() {
        testNavyWebServer.stop();
        Assertions.assertDoesNotThrow( () -> {
            testHttpClient.sendGETRequest( "/ping" );
        });
    }

    @Test
    void sending_request_to_dead_server_should_return_null() {
        testNavyWebServer.stop();
        Assertions.assertNull( testHttpClient.sendGETRequest( "/ping" ));
    }
}
