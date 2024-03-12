package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeClientOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeClientOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeClientOperationTestInterface upsertRuntimeClientOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeClientModelFactory runtimeClientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntimeClient_whenUpsertRuntimeClient_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeClient = runtimeClientModelFactory.create(runtime.getId(), clientId());

        final var changeContext = upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_CLIENT_CREATED));
    }

    @Test
    void givenRuntimeClient_whenUpsertRuntimeClient_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeClient = runtimeClientModelFactory.create(runtime.getId(),
                clientId());
        upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);

        final var changeContext = upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_CLIENT_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertRuntimeClient_thenException() {
        final var shard = 0;
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId(),
                clientId());
        final var exception = assertThrows(ServerSideBadRequestException.class, () -> upsertRuntimeClientOperation
                .upsertRuntimeClient(shard, runtimeClient));
        assertEquals(ExceptionQualifierEnum.DB_VIOLATION, exception.getQualifier());
    }

    @Test
    void givenRuntimeClient_whenUpsertRuntimeClient_thenIdempotencyViolation() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeClient1 = runtimeClientModelFactory.create(runtime.getId(),
                clientId());
        upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient1);

        final var runtimeClient2 = runtimeClientModelFactory.create(runtime.getId(),
                clientId(),
                runtimeClient1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient2));
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