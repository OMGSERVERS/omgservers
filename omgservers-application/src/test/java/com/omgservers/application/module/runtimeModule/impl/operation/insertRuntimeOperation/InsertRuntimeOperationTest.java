package com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation.SelectRuntimeOperation;
import com.omgservers.application.module.runtimeModule.model.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.RuntimeModel;
import com.omgservers.application.module.runtimeModule.model.RuntimeTypeEnum;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class InsertRuntimeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertRuntimeOperation insertRuntimeOperation;

    @Inject
    SelectRuntimeOperation selectRuntimeOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntime_whenInsertRuntime_thenInserted() {
        final var shard = 0;
        final var runtime1 = RuntimeModel.create(matchmakerUuid(), matchUuid(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        insertRuntimeOperation.insertRuntime(TIMEOUT, pgPool, shard, runtime1);

        final var runtime2 = selectRuntimeOperation.selectRuntime(TIMEOUT, pgPool, shard, runtime1.getUuid());
        assertEquals(runtime1, runtime2);
    }

    @Test
    void givenRuntime_whenInsertRuntimeAgain_thenServerSideConflictException() {
        final var shard = 0;
        final var runtime = RuntimeModel.create(matchmakerUuid(), matchUuid(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        insertRuntimeOperation.insertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var exception = assertThrows(ServerSideConflictException.class, () -> insertRuntimeOperation
                .insertRuntime(TIMEOUT, pgPool, shard, runtime));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID matchmakerUuid() {
        return UUID.randomUUID();
    }

    UUID matchUuid() {
        return UUID.randomUUID();
    }
}