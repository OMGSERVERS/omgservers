package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.shard.runtime.impl.operation.runtime.UpsertRuntimeOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.SelectActiveRuntimeCommandsByRuntimeIdOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.UpsertRuntimeCommandOperation;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusTest
class SelectRuntimeCommandsByRuntimeIdAndStatusOperationTest extends BaseTestClass {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectActiveRuntimeCommandsByRuntimeIdOperation selectActiveRuntimeCommandsByRuntimeIdOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    RuntimeMessageModelFactory runtimeMessageModelFactory;

    @Inject
    PgPool pgPool;

//    @Test
//    void givenRuntimeCommands_whenSelectNewRuntimeCommands_thenSelected() {
//        final var slot = 0;
//
//        final var runtime1 = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeQualifierEnum.SCRIPT, RuntimeAssignmentConfigDto.create());
//        upsertRuntimeOperation.upsertRuntime(slot, runtime1);
//        final var runtimeCommand11 = runtimeMessageModelFactory.create(runtime1.getId(), new InitMatchCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, slot, runtimeCommand11);
//        final var runtimeCommand12 = runtimeMessageModelFactory.create(runtime1.getId(), new StopMatchmakingCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, slot, runtimeCommand12);
//
//        final var runtime21 = runtimeModelFactory.create(tenantId(), stageId(), versionId(), matchmakerId(), matchId(), RuntimeQualifierEnum.SCRIPT, RuntimeAssignmentConfigDto.create());
//        upsertRuntimeOperation.upsertRuntime(slot, runtime21);
//
//        final var runtimeCommand21 = runtimeMessageModelFactory.create(runtime21.getId(), new InitMatchCommandBodyModel());
//        upsertRuntimeCommandOperation.upsertRuntimeCommand(TIMEOUT, pgPool, slot, runtimeCommand21);
//
//        final var newRuntimeCommands = selectActiveRuntimeCommandsByRuntimeIdOperation.selectRuntimeCommandsByRuntimeIdAndStatus(TIMEOUT, pgPool, slot, runtime1.getId());
//        assertEquals(2, newRuntimeCommands.size());
//        assertEquals(runtimeCommand11, newRuntimeCommands.get(0));
//        assertEquals(runtimeCommand12, newRuntimeCommands.get(1));
//    }
//
//    @Test
//    void givenUnknownRuntimeId_whenSelectNewRuntimeCommands_thenEmpty() {
//        final var slot = 0;
//        final var id = generateIdOperation.generateId();
//
//        final var newRuntimeCommands = selectActiveRuntimeCommandsByRuntimeIdOperation.selectRuntimeCommandsByRuntimeIdAndStatus(TIMEOUT, pgPool, slot, id);
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