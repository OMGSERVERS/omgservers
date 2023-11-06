package com.omgservers;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.utils.operation.bootstrapVersionOperation.BootstrapVersionOperation;
import com.omgservers.utils.operation.deleteVersionOperation.DeleteVersionOperation;
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
public class DoStopMatchIT extends Assertions {

    @TestHTTPResource("/omgservers/gateway")
    URI uri;

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Inject
    DeleteVersionOperation deleteVersionOperation;

    @Inject
    TestClientFactory testClientFactory;

    @Test
    void stopMatchTest() throws Exception {
        final var version = bootstrapVersionOperation.bootstrapVersion(
                """                                                                                              
                        local var command = ...
                                                
                        if command.qualifier == "add_client" then
                            return {
                                {
                                    qualifier = "stop",
                                    reason = "why not?"
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

        final var client1 = testClientFactory.create(uri);
        client1.signUp(version);

        final var welcome1 = client1.consumeWelcomeMessage();
        assertNotNull(welcome1);

        client1.requestMatchmaking("death-match");

        final var assignment1 = client1.consumeAssignmentMessage();
        assertNotNull(assignment1);

        client1.close();

        Thread.sleep(10000);

        deleteVersionOperation.deleteVersion(version);
        Thread.sleep(10000);
    }

    @Data
    @AllArgsConstructor
    static class TestMessage {
        String text;
    }
}
