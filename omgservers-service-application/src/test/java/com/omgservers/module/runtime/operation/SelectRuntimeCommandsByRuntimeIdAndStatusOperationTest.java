package com.omgservers.module.runtime.operation;

import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeId.SelectRuntimeCommandsByRuntimeIdOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
@QuarkusTest
class SelectRuntimeCommandsByRuntimeIdAndStatusOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectRuntimeCommandsByRuntimeIdOperation selectRuntimeCommandsByRuntimeIdOperation;

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
//        final var runtimeCommand11 = runtimeCommandModelFactory.create(runtime1.getId(), new InitMatchCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand11);
//        final var runtimeCommand12 = runtimeCommandModelFactory.create(runtime1.getId(), new StopMatchMatchmakerCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand12);
//
//        final var runtime21 = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeTypeEnum.SCRIPT, RuntimeConfigModel.create());
//        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime21);
//
//        final var runtimeCommand21 = runtimeCommandModelFactory.create(runtime21.getId(), new InitMatchCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, shard, runtimeCommand21);
//
//        final var newRuntimeCommands = selectRuntimeCommandsByRuntimeIdOperation.selectRuntimeCommandsByRuntimeIdAndStatus(TIMEOUT, pgPool, shard, runtime1.getId());
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
//        final var newRuntimeCommands = selectRuntimeCommandsByRuntimeIdOperation.selectRuntimeCommandsByRuntimeIdAndStatus(TIMEOUT, pgPool, shard, id);
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