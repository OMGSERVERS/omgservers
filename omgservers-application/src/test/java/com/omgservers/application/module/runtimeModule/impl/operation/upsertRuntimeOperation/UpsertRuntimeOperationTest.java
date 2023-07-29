package com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation;

import com.omgservers.application.module.runtimeModule.model.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.RuntimeModelFactory;
import com.omgservers.application.module.runtimeModule.model.RuntimeTypeEnum;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
        final var runtime1 = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        assertTrue(upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime1));
    }

    @Test
    void givenRuntime_whenInsertRuntimeAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
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