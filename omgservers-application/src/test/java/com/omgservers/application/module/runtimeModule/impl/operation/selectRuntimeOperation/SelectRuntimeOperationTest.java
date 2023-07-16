package com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.runtimeModule.impl.operation.insertRuntimeOperation.InsertRuntimeOperation;
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
class SelectRuntimeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectRuntimeOperation selectRuntimeOperation;

    @Inject
    InsertRuntimeOperation insertRuntimeOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntime_whenSelectRuntime_thenSelected() {
        final var shard = 0;
        final var runtime1 = RuntimeModel.create(matchmakerUuid(), matchUuid(), RuntimeConfigModel.create(RuntimeTypeEnum.EMBEDDED_LUA));
        insertRuntimeOperation.insertRuntime(TIMEOUT, pgPool, shard, runtime1);

        final var runtime2 = selectRuntimeOperation.selectRuntime(TIMEOUT, pgPool, shard, runtime1.getUuid());
        assertEquals(runtime1, runtime2);
    }

    @Test
    void givenUnknownUuid_whenSelectRuntime_thenServerSideNotFoundException() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectRuntimeOperation
                .selectRuntime(TIMEOUT, pgPool, shard, uuid));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID matchmakerUuid() {
        return UUID.randomUUID();
    }

    UUID matchUuid() {
        return UUID.randomUUID();
    }
}