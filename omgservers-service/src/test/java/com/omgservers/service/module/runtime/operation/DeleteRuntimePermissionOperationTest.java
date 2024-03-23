package com.omgservers.service.module.runtime.operation;

import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.factory.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.RuntimeModelFactory;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.service.module.runtime.operation.testInterface.DeleteRuntimeAssignmentOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeAssignmentOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimePermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteRuntimeAssignmentOperationTestInterface deleteRuntimeAssignmentOperation;

    @Inject
    UpsertRuntimeOperation upsertRuntimeOperation;

    @Inject
    UpsertRuntimeAssignmentOperationTestInterface upsertRuntimeAssignmentOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRuntimeAssignment_whenDeleteRuntimeAssignment_thenDeleted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigModel());
        upsertRuntimeOperation.upsertRuntime(TIMEOUT, pgPool, shard, runtime);

        final var runtimeAssignment = runtimeAssignmentModelFactory
                .create(runtime.getId(), clientId());
        upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment);

        final var changeContext = deleteRuntimeAssignmentOperation.deleteRuntimeAssignment(shard,
                runtime.getId(),
                runtimeAssignment.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenDeleteRuntimeAssignment_thenFalse() {
        final var shard = 0;
        final var runtimeId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRuntimeAssignmentOperation.deleteRuntimeAssignment(shard, runtimeId, id);
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

    Long shardKey() {
        return generateIdOperation.generateId();
    }

    Long entityId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }
}