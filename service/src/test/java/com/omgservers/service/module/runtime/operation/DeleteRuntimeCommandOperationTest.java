package com.omgservers.service.module.runtime.operation;

import com.omgservers.schema.model.runtime.RuntimeConfigModel;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.schema.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.DeleteRuntimeCommandOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeCommandOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimeCommandOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteRuntimeCommandOperationTestInterface deleteRuntimeCommandOperation;

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    UpsertRuntimeCommandOperationTestInterface upsertRuntimeCommandOperation;

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
        final var runtime = runtimeModelFactory.create(tenantId(), versionId(), RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimeCommand =
                runtimeCommandModelFactory.create(runtime.getId(), new InitRuntimeCommandBodyModel());
        upsertRuntimeCommandOperation.upsertRuntimeCommand(shard, runtimeCommand);

        final var changeContext =
                deleteRuntimeCommandOperation.deleteRuntimeCommand(shard, runtime.getId(), runtimeCommand.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenDeleteRuntimeCommand_thenFalse() {
        final var shard = 0;
        final var runtimeId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRuntimeCommandOperation.deleteRuntimeCommand(shard, runtimeId, id);
        assertFalse(changeContext.getResult());
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
}