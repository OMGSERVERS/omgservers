package com.omgservers.tester;

import com.omgservers.tester.operation.bootstrapService.BootstrapServiceOperation;
import com.omgservers.tester.test.BootstrapVersionTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@QuarkusTest
public class BootstrapVersionIT extends Assertions {

    @Inject
    BootstrapVersionTest bootstrapVersionTest;

    @Inject
    BootstrapServiceOperation bootstrapServiceOperation;

    @Test
    void bootstrapVersionIT() throws Exception {
        bootstrapServiceOperation.bootstrapService(
                List.of(
                        URI.create("http://localhost:10001"),
                        URI.create("http://localhost:10002")
                ),
                Map.of(
                        "omgservers-service-1", "omgservers-service-1",
                        "omgservers-service-2", "omgservers-service-2"
                )
        );
//        bootstrapVersionTest.testBootstrapVersion();
    }
}