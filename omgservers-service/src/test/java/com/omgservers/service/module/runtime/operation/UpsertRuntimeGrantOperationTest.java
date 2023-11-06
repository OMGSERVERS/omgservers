package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.RuntimeGrantModelFactory;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeGrantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeGrantOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeGrantOperationTestInterface upsertRuntimeGrantOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeGrantModelFactory runtimeGrantModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntimeGrant_whenUpsertRuntimeGrant_thenTrue() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeTypeEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeGrant = runtimeGrantModelFactory.create(runtime.getId(),
                shardKey(),
                entityId(),
                RuntimeGrantTypeEnum.MATCH_CLIENT);

        final var changeContext = upsertRuntimeGrantOperation.upsertRuntimeGrant(shard, runtimeGrant);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenRuntimeGrant_whenUpsertRuntimeGrant_thenFalse() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeTypeEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeGrant = runtimeGrantModelFactory.create(runtime.getId(),
                shardKey(),
                entityId(),
                RuntimeGrantTypeEnum.MATCH_CLIENT);
        upsertRuntimeGrantOperation.upsertRuntimeGrant(shard, runtimeGrant);

        final var changeContext = upsertRuntimeGrantOperation.upsertRuntimeGrant(shard, runtimeGrant);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertRuntimeGrant_thenException() {
        final var shard = 0;
        final var runtimeGrant = runtimeGrantModelFactory.create(runtimeId(),
                shardKey(),
                entityId(),
                RuntimeGrantTypeEnum.MATCH_CLIENT);
        assertThrows(ServerSideConflictException.class, () -> upsertRuntimeGrantOperation
                .upsertRuntimeGrant(shard, runtimeGrant));
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