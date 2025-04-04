package com.omgservers.service.operation.server;

import com.omgservers.BaseTestClass;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class GenerateSecureStringOperationTest extends BaseTestClass {

    @Inject
    GenerateSecureStringOperation generateSecureStringOperation;

    @Test
    void givenOperation_whenGenerateSecureString_thenGenerated() {
        final var value1 = generateSecureStringOperation.generateSecureString();
        final var value2 = generateSecureStringOperation.generateSecureString();
        assertNotEquals(value1, value2);
        log.info("Secure strings were generated, v1={}, v2={}", value1, value2);
    }
}