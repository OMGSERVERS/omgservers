package com.omgservers.platform.integrationtest;

import com.omgservers.platforms.integrationtest.operations.createVersionAndSignUpOperation.CreateVersionAndSignUpOperation;
import com.omgservers.platforms.integrationtest.testClient.TestClient;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
public class PlayerSignUpSignInTest extends Assertions {

    @Inject
    CreateVersionAndSignUpOperation createIndependentVersionOperation;

    @Inject
    TestClient testClient;

    @Test
    void givenVersion_whenSignUpSignIn() throws Exception {
        final var version = createIndependentVersionOperation.createVersionAndSignUp("""
                function player_signed_up(event, player)
                    player.respond("from player_signed_up")
                end
                                
                function player_signed_in(event, player)
                    player.respond("from player_signed_in")
                end
                                
                print("initialized")
                """);

        var event1 = testClient.consumeEventMessage();
        assertEquals("from player_signed_up", event1.getEvent().toString());
        Thread.sleep(5000);

        // Reconnect to sign-in test
        testClient.close();
        testClient.connect();
        testClient.signIn(version.getTenant(), version.getStage(), version.getSecret(), version.getPlayerUser(), version.getPlayerPassword());
        var event2 = testClient.consumeEventMessage();
        assertEquals("from player_signed_in", event2.getEvent().toString());
        testClient.close();

        Thread.sleep(5000);

        log.info("Finished");
    }
}
