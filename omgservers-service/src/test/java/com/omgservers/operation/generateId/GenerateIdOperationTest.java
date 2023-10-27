package com.omgservers.operation.generateId;

import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.operation.getConfig.GetConfigOperation;
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

    @Inject
    GetConfigOperation getConfigOperation;

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
        long nodeId = (id >> GenerateIdOperation.NODE_ID_OFFSET) & GenerateIdOperation.NODE_ID_MASK;
        long datacenterId = (id >> GenerateIdOperation.DATACENTER_ID_OFFSET) & GenerateIdOperation.DATACENTER_ID_MASK;
        long timestamp = (id >> GenerateIdOperation.TIMESTAMP_OFFSET);

        assertEquals(1, sequence);
        assertEquals(getConfigOperation.getConfig().nodeId(), nodeId);
        assertEquals(getConfigOperation.getConfig().datacenterId(), datacenterId);
        assertTrue(timestamp > 0);
    }
}