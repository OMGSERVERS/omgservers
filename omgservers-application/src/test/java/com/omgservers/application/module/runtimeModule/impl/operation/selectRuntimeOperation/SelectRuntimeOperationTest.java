package com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation.UpsertRuntimeOperation;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.application.factory.RuntimeModelFactory;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectRuntimeOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectRuntimeOperation selectRuntimeOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntime_whenSelectRuntime_thenSelected() {
        final var shard = 0;
        final var runtime1 = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime1);

        final var runtime2 = selectRuntimeOperation.selectRuntime(TIMEOUT, pgPool, shard, runtime1.getId());
        assertEquals(runtime1, runtime2);
    }

    @Test
    void givenUnknownUuid_whenSelectRuntime_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectRuntimeOperation
                .selectRuntime(TIMEOUT, pgPool, shard, id));
        log.info("Exception: {}", exception.getMessage());
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}