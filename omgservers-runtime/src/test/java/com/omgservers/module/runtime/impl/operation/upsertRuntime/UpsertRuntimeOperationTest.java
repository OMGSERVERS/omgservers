package com.omgservers.module.runtime.impl.operation.upsertRuntime;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.factory.RuntimeModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

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
        final var runtime1 = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        assertTrue(upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime1));
    }

    @Test
    void givenRuntime_whenInsertRuntimeAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        assertFalse(upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime));
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}