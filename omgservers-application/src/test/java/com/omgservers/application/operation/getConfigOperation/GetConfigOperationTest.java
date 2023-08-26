package com.omgservers.application.operation.getConfigOperation;

import com.omgservers.base.operation.getConfig.GetConfigOperation;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import java.net.URI;

@Slf4j
@QuarkusTest
class GetConfigOperationTest extends Assertions {

    @Inject
    GetConfigOperation getConfigOperation;

    @Test
    void givenServer_whenServerUri_thenEqual() {
        assertEquals(URI.create("http://localhost:8080"), getConfigOperation.getConfig().serverUri());
    }
}