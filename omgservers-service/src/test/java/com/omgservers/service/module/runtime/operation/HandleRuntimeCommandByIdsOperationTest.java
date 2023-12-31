package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class HandleRuntimeCommandByIdsOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntime_whenUpsertRuntime_thenInserted() {
        final var shard = 0;
        final var runtime1 =
                runtimeModelFactory.create(tenantId(), versionId(), RuntimeQualifierEnum.MATCH, new RuntimeConfigModel());
        assertTrue(upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime1));
    }

    @Test
    void givenRuntime_whenInsertRuntimeAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), versionId(), RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        assertFalse(upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime));
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
}