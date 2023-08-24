package com.omgservers.application.module.runtimeModule.impl.operation.upsertCommandOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation.UpsertRuntimeOperation;
import com.omgservers.application.module.runtimeModule.model.command.CommandModelFactory;
import com.omgservers.application.module.runtimeModule.model.command.body.StartCommandBodyModel;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeConfigModel;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeModelFactory;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeTypeEnum;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertCommandOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertCommandOperation upsertCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    CommandModelFactory commandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenCommand_whenUpsertCommand_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var command = commandModelFactory.create(runtime.getId(), new StartCommandBodyModel());
        assertTrue(upsertCommandOperation.upsertCommand(TIMEOUT, pgPool, shard, command));
    }

    @Test
    void givenCommand_whenUpserCommandAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var command = commandModelFactory.create(runtime.getId(), new StartCommandBodyModel());
        upsertCommandOperation.upsertCommand(TIMEOUT, pgPool, shard, command);

        assertFalse(upsertCommandOperation.upsertCommand(TIMEOUT, pgPool, shard, command));
    }

    @Test
    void givenUnknownRuntimeUuid_whenUpsertCommand_thenServerSideNotFoundException() {
        final var shard = 0;
        final var command = commandModelFactory.create(runtimeId(), new StartCommandBodyModel());
        final var exception = assertThrows(ServerSideNotFoundException.class, () -> upsertCommandOperation
                .upsertCommand(TIMEOUT, pgPool, shard, command));
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