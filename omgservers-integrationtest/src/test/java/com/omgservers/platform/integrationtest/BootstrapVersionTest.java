package com.omgservers.platform.integrationtest;

import com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation.BootstrapVersionOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
public class BootstrapVersionTest extends Assertions {

    @Inject
    BootstrapVersionOperation bootstrapVersionOperation;

    @Test
    void whenBootstrapVersion_thenFinished() throws Exception {
        bootstrapVersionOperation.bootstrap("print(\"version was initialized\")");
    }
}
