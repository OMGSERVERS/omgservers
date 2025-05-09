package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.shard.runtime.operation.testInterface.UpsertRuntimeAssignmentOperationTestInterface;
import com.omgservers.service.shard.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimePoolContainerRefOperationTest extends BaseTestClass {

    @Inject
    UpsertRuntimeAssignmentOperationTestInterface upsertRuntimeAssignmentOperation;

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Test
    void givenRuntimeAssignment_whenUpsertRuntimeAssignment_thenInserted() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(slot, runtime);

        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtime.getId(), clientId());

        final var changeContext = upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(slot, runtimeAssignment);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_ASSIGNMENT_CREATED));
    }

    @Test
    void givenRuntimeAssignment_whenUpsertRuntimeAssignment_thenSkip() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(slot, runtime);

        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtime.getId(),
                clientId());
        upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(slot, runtimeAssignment);

        final var changeContext = upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(slot, runtimeAssignment);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_ASSIGNMENT_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertRuntimeAssignment_thenException() {
        final var slot = 0;
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId(),
                clientId());
        final var exception = assertThrows(ServerSideBadRequestException.class, () -> upsertRuntimeAssignmentOperation
                .upsertRuntimeAssignment(slot, runtimeAssignment));
        assertEquals(ExceptionQualifierEnum.DB_CONSTRAINT_VIOLATED, exception.getQualifier());
    }

    @Test
    void givenRuntimeAssignment_whenUpsertRuntimeAssignment_thenIdempotencyViolation() {
        final var slot = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(slot, runtime);

        final var runtimeAssignment1 = runtimeAssignmentModelFactory.create(runtime.getId(),
                clientId());
        upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(slot, runtimeAssignment1);

        final var runtimeAssignment2 = runtimeAssignmentModelFactory.create(runtime.getId(),
                clientId(),
                runtimeAssignment1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(slot, runtimeAssignment2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }
}