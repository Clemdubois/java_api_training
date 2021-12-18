package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.web_server.NavyWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class LauncherTest {

    // increment testPort between each step to avoid unwanted errors
    private static int testPort = 10000;

    @AfterEach
    public void increment_testPort() {
        testPort++;
    }

    @Test
    public void wrong_port_arg_should_throw_in_main() {
        Assertions.assertThrows( Exception.class, () -> Launcher.main(new String[] {"123456789"}));
    }

    @Test
    public void wrong_type_port_arg_should_throw_in_main() {
        Assertions.assertThrows( Exception.class, () -> Launcher.main(new String[] {"thisIsNotAPort"}));
    }

    @Test
    public void none_port_arg_should_throw() {
        Assertions.assertThrows( Exception.class, () -> Launcher.main(new String[] {" "}));
    }

    @Test
    void bound_port_already_existing_should_throw_IOException() throws IOException {
        new NavyWebServer(testPort);
        Assertions.assertThrows( IOException.class, () -> Launcher.main(new String[] { Integer.toString(testPort) }));
    }
/*
    @Test
    public void correct_port_shouldnt_throw_main() {
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] { Integer.toString(testPort) }));
    }

    @Test
    public void correct_port_and_address_given_shouldnt_throw() {
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] { Integer.toString(testPort++) }));
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] { Integer.toString(testPort), "http://localhost:"+(testPort-1) }));
    }*/

    @Test
    public void main_should_not_throw_exception() {
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] {}));
    }
}
