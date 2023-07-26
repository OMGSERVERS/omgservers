package com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation.SelectRuntimeOperation;
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
class InsertRuntimeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertRuntimeOperation insertRuntimeOperation;

    @Inject
    SelectRuntimeOperation selectRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntime_whenInsertRuntime_thenInserted() {
        final var shard = 0;
        final var runtime1 = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        insertRuntimeOperation.insertRuntime(TIMEOUT, pgPool, shard, runtime1);

        final var runtime2 = selectRuntimeOperation.selectRuntime(TIMEOUT, pgPool, shard, runtime1.getId());
        assertEquals(runtime1, runtime2);
    }

    @Test
    void givenRuntime_whenInsertRuntimeAgain_thenServerSideConflictException() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        insertRuntimeOperation.insertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var exception = assertThrows(ServerSideConflictException.class, () -> insertRuntimeOperation
                .insertRuntime(TIMEOUT, pgPool, shard, runtime));
        log.info("Exception: {}", exception.getMessage());
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}