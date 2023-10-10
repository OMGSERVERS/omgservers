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
public class RuntimeStopMatchTest extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void runtimeStopMatchTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion("""                        
                        local var event = context.event
                        local var state = context.state

                        print("event: " .. event.id)
                                               
                        if event.id == "add_client" then
                            context.stop_runtime("why not?")
                        end
                                                
                        """,
                new VersionConfigModel(new ArrayList<>() {{
                    add(VersionModeModel.create("death-match", 1, 16, new ArrayList<>() {{
                        add(new VersionGroupModel("players", 1, 16));
                    }}));
                }}));

        final var client1 = testClientFactory.create(uri);
        client1.signUp(version);

        final var welcome1 = client1.consumeWelcomeMessage();
        assertNotNull(welcome1);

        client1.requestMatchmaking("death-match");

        final var assignment1 = client1.consumeAssignmentMessage();
        assertNotNull(assignment1);

        Thread.sleep(5000);

        client1.close();
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
