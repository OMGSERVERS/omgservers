package com.omgservers.application.module.runtimeModule.impl.operation.deleteCommandOperation;

import com.omgservers.application.module.runtimeModule.impl.operation.upsertCommandOperation.UpsertCommandOperation;
import com.omgservers.application.module.runtimeModule.impl.operation.upsertRuntimeOperation.UpsertRuntimeOperation;
import com.omgservers.application.factory.RuntimeCommandModelFactory;
import com.omgservers.model.runtimeCommand.body.StartRuntimeCommandBodyModel;
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
class DeleteCommandOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteCommandOperation deleteCommandOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertCommandOperation upsertCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeCommandModelFactory commandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenCommand_whenDeleteCommand_thenDeleted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var command = commandModelFactory.create(runtime.getId(), new StartRuntimeCommandBodyModel());
        upsertCommandOperation.upsertCommand(TIMEOUT, pgPool, shard, command);

        assertTrue(deleteCommandOperation.deleteCommand(TIMEOUT, pgPool, shard, command.getId()));
    }

    @Test
    void givenUnknownUuid_whenDeleteCommand_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteCommandOperation.deleteCommand(TIMEOUT, pgPool, shard, id));
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}