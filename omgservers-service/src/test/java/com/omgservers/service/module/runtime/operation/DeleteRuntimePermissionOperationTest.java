package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.factory.RuntimeGrantModelFactory;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.service.module.runtime.operation.testInterface.DeleteRuntimeGrantOperationTestInterface;
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
class DeleteRuntimePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteRuntimeGrantOperationTestInterface deleteRuntimeGrant;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertRuntimeGrantOperationTestInterface upsertRuntimeGrantOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeGrantModelFactory runtimeGrantModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntimeGrant_whenDeleteRuntimeGrant_thenDeleted() {
        final var shard = 0;
        final var runtime =
                runtimeModelFactory.create(tenantId(), versionId(), RuntimeTypeEnum.MATCH, new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeGrant = runtimeGrantModelFactory
                .create(runtime.getId(), shardKey(), entityId(), RuntimeGrantTypeEnum.MATCH_CLIENT);
        upsertRuntimeGrantOperation.upsertRuntimeGrant(shard, runtimeGrant);

        final var changeContext = deleteRuntimeGrant.deleteRuntimeGrant(shard, runtime.getId(), runtimeGrant.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenDeleteRuntimeGrant_thenFalse() {
        final var shard = 0;
        final var runtimeId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRuntimeGrant.deleteRuntimeGrant(shard, runtimeId, id);
        assertFalse(changeContext.getResult());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }
}