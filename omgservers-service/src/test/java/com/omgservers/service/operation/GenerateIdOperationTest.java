package com.omgservers.service.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class GenerateIdOperationTest extends BaseTestClass {

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    GetConfigOperation getConfigOperation;

    @Test
    void sequenceTest() throws InterruptedException {
        long v1 = generateIdOperation.generateId();
        long v2 = generateIdOperation.generateId();
        Thread.sleep(1);
        long v3 = generateIdOperation.generateId();
        long v4 = generateIdOperation.generateId();
        assertTrue(v4 > v3);
        assertTrue(v3 > v2);
        assertTrue(v2 > v1);
    }

    @Test
    void overflowTest() {
        assertThrows(ServerSideInternalException.class, () -> {
            for (int i = 0; i < 8192; i++) {
                generateIdOperation.generateId();
            }
        });
    }

    @Test
    void structureTest() throws InterruptedException {
        Thread.sleep(1);
        // Skip sequence 0 for test purpose
        generateIdOperation.generateId();
        long id = generateIdOperation.generateId();

        long sequence = id & GenerateIdOperation.SEQUENCE_MASK;
        long instanceId = (id >> GenerateIdOperation.INSTANCE_ID_OFFSET) & GenerateIdOperation.INSTANCE_ID_MASK;
        long timestamp = (id >> GenerateIdOperation.TIMESTAMP_OFFSET);

        assertEquals(1, sequence);
        assertEquals(getConfigOperation.getServiceConfig().server().instanceId(), instanceId);
        assertTrue(timestamp > 0);
    }
}