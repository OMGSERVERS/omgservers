package com.omgservers.service.module.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtime.RuntimeQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.runtime.RuntimeModelFactory;
import com.omgservers.service.module.runtime.operation.testInterface.DeleteRuntimeAssignmentOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeAssignmentOperationTestInterface;
import com.omgservers.service.module.runtime.operation.testInterface.UpsertRuntimeOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimePermissionOperationTest extends BaseTestClass {

    @Inject
    DeleteRuntimeAssignmentOperationTestInterface deleteRuntimeAssignmentOperation;

    @Inject
    UpsertRuntimeOperationTestInterface upsertRuntimeOperation;

    @Inject
    UpsertRuntimeAssignmentOperationTestInterface upsertRuntimeAssignmentOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    RuntimeModelFactory runtimeModelFactory;

    @Inject
    RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Test
    void givenRuntimeAssignment_whenDeleteRuntimeAssignment_thenDeleted() {
        final var shard = 0;
        final var runtime = runtimeModelFactory.create(tenantId(),
                versionId(),
                RuntimeQualifierEnum.MATCH,
                new RuntimeConfigDto());
        upsertRuntimeOperation.upsertRuntime(shard, runtime);

        final var runtimeAssignment = runtimeAssignmentModelFactory
                .create(runtime.getId(), clientId());
        upsertRuntimeAssignmentOperation.upsertRuntimeAssignment(shard, runtimeAssignment);

        final var changeContext = deleteRuntimeAssignmentOperation.deleteRuntimeAssignment(shard,
                runtime.getId(),
                runtimeAssignment.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.RUNTIME_ASSIGNMENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteRuntimeAssignment_thenSkip() {
        final var shard = 0;
        final var runtimeId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRuntimeAssignmentOperation.deleteRuntimeAssignment(shard, runtimeId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.RUNTIME_ASSIGNMENT_DELETED));
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