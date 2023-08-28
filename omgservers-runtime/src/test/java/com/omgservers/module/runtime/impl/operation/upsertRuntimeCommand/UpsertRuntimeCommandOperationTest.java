package com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.factory.RuntimeCommandModelFactory;
import com.omgservers.factory.RuntimeModelFactory;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeCommandOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeCommandModelFactory commandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenCommand_whenUpsertRuntimeCommand_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var command = commandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        assertTrue(upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, command));
    }

    @Test
    void givenCommand_whenUpsertRuntimeCommandAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = commandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand);

        assertFalse(upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand));
    }

    @Test
    void givenUnknownRuntimeId_whenUpsertRuntimeCommand_thenServerSideNotFoundException() {
        final var shard = 0;
        final var command = commandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertRuntimeCommandOperation
                .upsertRuntimeCommand(TIMEOUT, pgPool, shard, command));
        log.info("Exception: {}", exception.getMessage());
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}