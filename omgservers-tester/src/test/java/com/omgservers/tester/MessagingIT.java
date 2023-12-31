package com.omgservers.tester;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.testClient.TestClientFactory;
import com.omgservers.tester.operation.bootstrapTestVersion.BootstrapTestVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class MessagingIT extends Assertions {

    @Inject
    BootstrapTestVersionOperation bootstrapTestVersionOperation;

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void messagingIT() throws Exception {
        final var version = bootstrapTestVersionOperation.bootstrapTestVersion(
                """
                        """,
                """
                        local var command = ...
                                                
                        if command.qualifier == "init_runtime" then
                            clients = {}
                        end
                                                
                        if command.qualifier == "add_client" then
                            table.insert(clients, {
                                user_id = command.user_id,
                                client_id = command.client_id
                            })
                            
                            return {
                                {
                                    qualifier = "unicast",
                                    user_id = command.user_id,
                                    client_id = command.client_id,
                                    message = {
                                        text = "hello, client"
                                    }
                                }
                            }
                        end
                                                
                        if command.qualifier == "update_runtime" then
                           
                            if #clients >= 2 then
                                local client_1 = clients[1]
                                local client_2 = clients[2]
                                
                                return {
                                    {
                                        qualifier = "multicast",
                                        recipients = {
                                            {
                                                user_id = client_1.user_id,
                                                client_id = client_1.client_id
                                            },
                                            {
                                                user_id = client_2.user_id,
                                                client_id = client_2.client_id
                                            }
                                        },
                                        message = {
                                            text = "hello, client_1 and client_2"
                                        }
                                    },
                                    {
                                        qualifier = "broadcast",
                                        message = {
                                            text = "hello, all"
                                        }
                                    }
                                }
                            end
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 2, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 2, 16));
                    }}));
                }}));

        Thread.sleep(10000);

        try {

            final var client1 = testClientFactory.create();
            client1.signUp(version);
            final var client2 = testClientFactory.create();
            client2.signUp(version);

            final var welcome1 = client1.consumeWelcomeMessage();
            assertNotNull(welcome1);
            final var welcome2 = client2.consumeWelcomeMessage();
            assertNotNull(welcome2);

            client1.requestMatchmaking("death-match");
            client2.requestMatchmaking("death-match");

            final var assignment1 = client1.consumeAssignmentMessage();
            assertNotNull(assignment1);
            final var assignment2 = client2.consumeAssignmentMessage();
            assertNotNull(assignment2);

            final var event11 = client1.consumeServerMessage();
            assertEquals("{text=hello, client}", event11.getMessage().toString());

            final var event21 = client2.consumeServerMessage();
            assertEquals("{text=hello, client}", event21.getMessage().toString());

            final var event12 = client1.consumeServerMessage();
            assertEquals("{text=hello, client_1 and client_2}", event12.getMessage().toString());

            final var event22 = client2.consumeServerMessage();
            assertEquals("{text=hello, client_1 and client_2}", event22.getMessage().toString());

            final var event13 = client1.consumeServerMessage();
            assertEquals("{text=hello, all}", event13.getMessage().toString());

            final var event23 = client2.consumeServerMessage();
            assertEquals("{text=hello, all}", event23.getMessage().toString());

            client1.close();
            client2.close();

            Thread.sleep(10000);
        } finally {
            adminApiTester.deleteTenant(version.getTenantId());
            Thread.sleep(10000);
        }
    }
}
