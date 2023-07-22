package com.omgservers.application.operation.generateIdOperation;

import com.omgservers.application.exception.ServerSideInternalException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class GenerateIdOperationTest extends Assertions {

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void sequenceTest() {
        long v1 = generateIdOperation.generateId();
        long v2 = generateIdOperation.generateId();
        long v3 = generateIdOperation.generateId();
        assertTrue(v3 > v2 && v2 > v1);
    }

    @Test
    void overflowTest() {
        assertThrows(ServerSideInternalException.class, () -> {
            for (int i = 0; i < 2048; i++) {
                generateIdOperation.generateId();
            }
        });
    }
}