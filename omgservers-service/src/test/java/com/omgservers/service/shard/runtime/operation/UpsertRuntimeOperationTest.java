package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeOperationTest extends BaseTestClass {

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRuntime_whenExecute_thenInserted() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(deploymentId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        final var changeContext = upsertRuntimeOperation.upsertRuntime(slot, runtime);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_CREATED));
    }

    @Test
    void givenRuntime_whenExecute_thenUpdated() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(deploymentId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(slot, runtime);

        final var changeContext = upsertRuntimeOperation.upsertRuntime(slot, runtime);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_CREATED));
    }

    @Test
    void givenRuntime_whenExecute_thenIdempotencyViolation() {
        final var slot = 0;
        final var runtime1 = runtimeModelFactory.create(deploymentId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(slot, runtime1);

        final var runtime2 = runtimeModelFactory.create(deploymentId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto(),
                runtime1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertRuntimeOperation.upsertRuntime(slot, runtime2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long deploymentId() {
        return generateIdOperation.generateId();
    }
}