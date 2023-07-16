package com.omgservers.platform.integrationtest;

import com.omgservers.platforms.integrationtest.operations.createVersionAndSignUpOperation.CreateVersionAndSignUpOperation;
import com.omgservers.platforms.integrationtest.testClient.TestClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PlayerSetGetAttributeTest extends Assertions {

    @Inject
    CreateVersionAndSignUpOperation createIndependentVersionOperation;

    @Inject
    TestClient testClient;

    @Test
    void givenVersion_whenSetGetAttribute() throws Exception {
        final var version = createIndependentVersionOperation.createVersionAndSignUp("""
                function player_signed_up(event, player)
                    player.set_attribute("a1", "helloworld")
                    player.set_attribute("a2", 0)
                    player.set_attribute("a3", 123456789)
                end

                function player_signed_in(event, player)
                    local a1 = player.get_attribute("a1")
                    local a2 = player.get_attribute("a2")
                    local a3 = player.get_attribute("a3")
                    print(a1, a2, a3)
                    
                    local attributes = table.concat({a1, a2, a3}, ";")
                    player.respond(attributes)
                end

                print("initialized")
                """);
        testClient.close();
        testClient.connect();
        testClient.signIn(version.getTenant(), version.getStage(), version.getSecret(), version.getPlayerUser(), version.getPlayerPassword());
        var message = testClient.consumeEventMessage();
        assertEquals("helloworld;0;123456789", message.getEvent().toString());
        testClient.close();

        testClient.close();

        log.info("Finished");
    }
}
