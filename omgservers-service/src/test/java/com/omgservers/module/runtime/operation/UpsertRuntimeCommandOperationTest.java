package com.omgservers.module.runtime.operation;

import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
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
class UpsertRuntimeCommandOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

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
    void givenCommand_whenUpsertRuntimeCommand_thenInserted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), versionId(), RuntimeTypeEnum.EMBEDDED_MATCH_SCRIPT, new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        assertTrue(upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand));
    }

    @Test
    void givenCommand_whenUpsertRuntimeCommandAgain_thenUpdated() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(), versionId(), RuntimeTypeEnum.EMBEDDED_MATCH_SCRIPT, new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeCommand = runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand);

        assertFalse(upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand));
    }

    @Test
    void givenUnknownRuntimeId_whenUpsertRuntimeCommand_thenException() {
        final var shard = 0;
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId(), new InitRuntimeCommandBodyModel());
        final var exception = assertThrows(ServerSideConflictException.class, () -> upsertRuntimeCommandOperation
                .upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }
}