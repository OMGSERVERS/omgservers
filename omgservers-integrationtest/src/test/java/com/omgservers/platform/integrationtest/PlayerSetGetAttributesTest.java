package com.omgservers.platform.integrationtest;

import com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation.BootstrapVersionOperation;
import com.omgservers.platforms.integrationtest.serviceClient.ServiceClientFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class PlayerSetGetAttributesTest extends Assertions {

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    ServiceClientFactory serviceClientFactory;

    @Test
    void givenVersion_whenSetGetAttributes() throws Exception {
        final var version = bootstrapVersionOperation.bootstrap("""
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

                print("version was initialized")
                """);

        final var client = serviceClientFactory.create();
        client.signUp(version);

        Thread.sleep(1000);

        client.reconnect();
        client.signIn(version);
        var message = client.consumeEventMessage();
        assertEquals("helloworld;0;123456789", message.getEvent().toString());
        client.close();

        log.info("Finished");
    }
}
