package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeAssignmentOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeAssignmentOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeAssignmentOperationTestInterface upsertRuntimeAssignmentOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntimeAssignment_whenUpsertRuntimeAssignment_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtime.getId(), clientId());

        final var changeContext = upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_ASSIGNMENT_CREATED));
    }

    @Test
    void givenRuntimeAssignment_whenUpsertRuntimeAssignment_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtime.getId(),
                clientId());
        upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment);

        final var changeContext = upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_ASSIGNMENT_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertRuntimeAssignment_thenException() {
        final var shard = 0;
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId(),
                clientId());
        final var exception = assertThrows(ServerSideBadRequestException.class, () -> upsertRuntimeAssignmentOperation
                .upsertRuntimeAssignment(shard, runtimeAssignment));
        assertEquals(ExceptionQualifierEnum.DB_VIOLATION, exception.getQualifier());
    }

    @Test
    void givenRuntimeAssignment_whenUpsertRuntimeAssignment_thenIdempotencyViolation() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeAssignment1 = runtimeAssignmentModelFactory.create(runtime.getId(),
                clientId());
        upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment1);

        final var runtimeAssignment2 = runtimeAssignmentModelFactory.create(runtime.getId(),
                clientId(),
                runtimeAssignment1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
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