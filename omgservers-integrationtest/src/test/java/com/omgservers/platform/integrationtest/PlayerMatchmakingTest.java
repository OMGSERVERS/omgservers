package com.omgservers.platform.integrationtest;

import com.omgservers.application.module.versionModule.model.VersionGroupModel;
import com.omgservers.application.module.versionModule.model.VersionModeModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation.BootstrapVersionOperation;
import com.omgservers.platforms.integrationtest.serviceClient.ServiceClientFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class PlayerMatchmakingTest extends Assertions {

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    ServiceClientFactory serviceClientFactory;

    @Test
    void matchmakingTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrap("""
                        function player_signed_up(event, player)
                        end

                        function player_signed_in(event, player)
                            player.respond("signed_in")
                        end

                        print("version was initialized")
                        """,
                new VersionStageConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 2, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 2, 16));
                    }}));
                }}));

        final var client1 = serviceClientFactory.create();
        client1.signUp(version);
        client1.reconnect();
        client1.signIn(version);

        final var client2 = serviceClientFactory.create();
        client2.signUp(version);
        client2.reconnect();
        client2.signIn(version);

        final var event1 = client1.consumeEventMessage();
        assertEquals("signed_in", event1.getEvent().toString());
        client1.requestMatchmaking("death-match");

        final var event2 = client2.consumeEventMessage();
        assertEquals("signed_in", event2.getEvent().toString());
        client2.requestMatchmaking("death-match");

        Thread.sleep(5000);

        client1.close();
        client2.close();
        log.info("Finished");
    }
}
