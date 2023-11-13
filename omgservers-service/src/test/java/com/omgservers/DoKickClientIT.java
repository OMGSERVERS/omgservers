package com.omgservers;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.utils.AdminCli;
import com.omgservers.utils.operation.bootstrapVersionOperation.BootstrapVersionOperation;
import com.omgservers.utils.testClient.TestClientFactory;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class DoKickClientIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    AdminCli adminCli;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void doKickClientTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""
                        local var command = ...
                                               
                        if command.qualifier == "add_client" then
                            local var user_id = command.user_id
                            local var client_id = command.client_id
                            
                            if admin then
                                return {
                                    {
                                        qualifier = "kick",
                                        user_id = command.user_id,
                                        client_id = command.client_id
                                    }
                                }
                            else
                                admin = {
                                    user_id = command.user_id,
                                    client_id = command.client_id
                                }
                            end
                        end
                                                
                        if command.qualifier == "delete_client" then
                            return {
                                {
                                    qualifier = "broadcast",
                                    message = {
                                        text = "kicked"
                                    }
                                }
                            }
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }}));

        Thread.sleep(10000);

        try {


            final var client1 = testClientFactory.create(uri);
            client1.signUp(version);
            final var client2 = testClientFactory.create(uri);
            client2.signUp(version);

            final var welcome1 = client1.consumeWelcomeMessage();
            assertNotNull(welcome1);
            final var welcome2 = client2.consumeWelcomeMessage();
            assertNotNull(welcome2);

            client1.requestMatchmaking("death-match");

            final var assignment1 = client1.consumeAssignmentMessage();
            assertNotNull(assignment1);

            client2.requestMatchmaking("death-match");

            final var assignment2 = client2.consumeAssignmentMessage();
            assertNotNull(assignment2);

            final var event1 = client2.consumeRevocationMessage();
            assertNotNull(event1);

            final var event2 = client1.consumeServerMessage();
            assertEquals("{text=kicked}", event2.getMessage().toString());

            client1.close();
            client2.close();

            Thread.sleep(10000);
        } finally {
            adminCli.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
