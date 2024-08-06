package com.omgservers.tester.defold;

import com.omgservers.tester.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

@Slf4j
@QuarkusTest
public class DefoldLobbyRuntimeIT extends BaseTestClass {

    GenericContainer genericContainer;

    @Test
    void defoldLobbyRuntimeIT() throws Exception {
        genericContainer = new GenericContainer("omgservers/defold-test-runtime:1.0.0-SNAPSHOT")
                .withNetworkMode("host");
        genericContainer.start();

        genericContainer.stop();
    }
}
