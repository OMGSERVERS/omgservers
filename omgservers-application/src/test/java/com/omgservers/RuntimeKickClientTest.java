package com.omgservers;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.utils.operation.BootstrapVersionOperation;
import com.omgservers.utils.testClient.TestClientFactory;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
@QuarkusTest
public class RuntimeKickClientTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void runtimeKickClientTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""                        
                        local var event = context.event
                        local var state = context.state

                        print("event: " .. event.id)
                                                
                        if event.id == "init_runtime" then
                            state.flag = false
                        end
                                               
                        if event.id == "add_client" then
                            local var user_id = event.user_id
                            local var client_id = event.client_id
                            
                            if state.flag then                                
                                context.kick_client(user_id, client_id)
                            else
                                state.flag = true
                            end
                        end
                                                
                        if event.id == "delete_client" then
                            context.broadcast_message({text="client2 was kicked"})
                        end
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }}));

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
        assertEquals("{text=client2 was kicked}", event2.getMessage().toString());

        client1.close();
        client2.close();
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
