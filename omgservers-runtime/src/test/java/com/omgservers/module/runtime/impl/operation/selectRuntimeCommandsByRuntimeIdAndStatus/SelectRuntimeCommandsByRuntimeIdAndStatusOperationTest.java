package com.omgservers.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeIdAndStatus;

import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;
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
class SelectRuntimeCommandsByRuntimeIdAndStatusOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectRuntimeCommandsByRuntimeIdAndStatusOperation selectRuntimeCommandsByRuntimeIdAndStatusOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Inject
    PgPool pgPool;

//    @Test
//    void givenRuntimeCommands_whenSelectNewRuntimeCommands_thenSelected() {
//        final var shard = 0;
//
//        final var runtime1 = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.SCRIPT, RuntimeConfigModel.create());
//        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime1);
//        final var runtimeCommand11 = runtimeCommandModelFactory.create(runtime1.getId(), new InitRuntimeCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand11);
//        final var runtimeCommand12 = runtimeCommandModelFactory.create(runtime1.getId(), new StopRuntimeCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand12);
//
//        final var runtime21 = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.SCRIPT, RuntimeConfigModel.create());
//        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime21);
//
//        final var runtimeCommand21 = runtimeCommandModelFactory.create(runtime21.getId(), new InitRuntimeCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand21);
//
//        final var newRuntimeCommands = selectRuntimeCommandsByRuntimeIdAndStatusOperation.selectRuntimeCommandsByRuntimeIdAndStatus(TIMEOUT, pgPool, shard, runtime1.getId());
//        assertEquals(2, newRuntimeCommands.size());
//        assertEquals(runtimeCommand11, newRuntimeCommands.get(0));
//        assertEquals(runtimeCommand12, newRuntimeCommands.get(1));
//    }
//
//    @Test
//    void givenUnknownRuntimeId_whenSelectNewRuntimeCommands_thenEmpty() {
//        final var shard = 0;
//        final var id = generateIdOperation.generateId();
//
//        final var newRuntimeCommands = selectRuntimeCommandsByRuntimeIdAndStatusOperation.selectRuntimeCommandsByRuntimeIdAndStatus(TIMEOUT, pgPool, shard, id);
//        assertTrue(newRuntimeCommands.isEmpty());
//    }

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
}