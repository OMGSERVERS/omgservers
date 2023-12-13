package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
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
    void givenRuntimeClient_whenUpsertRuntimeClient_thenTrue() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeClient = runtimeClientModelFactory.create(runtime.getId(),
                shardKey(),
                entityId());

        final var changeContext = upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenRuntimeClient_whenUpsertRuntimeClient_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeClient = runtimeClientModelFactory.create(runtime.getId(),
                shardKey(),
                entityId());
        upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);

        final var changeContext = upsertRuntimeClientOperation.upsertRuntimeClient(shard, runtimeClient);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertRuntimeClient_thenException() {
        final var shard = 0;
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId(),
                shardKey(),
                entityId());
        assertThrows(ServerSideConflictException.class, () -> upsertRuntimeClientOperation
                .upsertRuntimeClient(shard, runtimeClient));
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

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}