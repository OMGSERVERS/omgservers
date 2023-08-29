package com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand;

import com.omgservers.factory.RuntimeCommandModelFactory;
import com.omgservers.factory.RuntimeModelFactory;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimeCommandOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteRuntimeCommandOperation deleteRuntimeCommandOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntimeCommand_whenDeleteRuntimeCommand_thenDeleted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), stageId(), matchmakerId(), matchId(), RuntimeTypeEnum.EMBEDDED_LUA, RuntimeConfigModel.create());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand);

        assertTrue(deleteRuntimeCommandOperation.deleteRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand.getId()));
    }

    @Test
    void givenUnknownRuntimeId_whenDeleteRuntimeCommand_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteRuntimeCommandOperation.deleteRuntimeCommand(TIMEOUT, pgPool, shard, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }
}