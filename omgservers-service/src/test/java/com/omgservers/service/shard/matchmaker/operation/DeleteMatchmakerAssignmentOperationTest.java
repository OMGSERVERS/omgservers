package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.shard.matchmaker.operation.testInterface.DeleteMatchmakerAssignmentOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerAssignmentOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteMatchmakerAssignmentOperationTest extends BaseTestClass {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerAssignmentOperationTestInterface upsertMatchmakerAssignmentOperation;

    @Inject
    DeleteMatchmakerAssignmentOperationTestInterface deleteMatchmakerAssignmentOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerAssignment_whenExecute_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmaker.getId(), clientId());
        upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard, matchmakerAssignment);

        final var changeContext = deleteMatchmakerAssignmentOperation.deleteMatchmakerAssignment(shard,
                matchmaker.getId(),
                matchmakerAssignment.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_ASSIGNMENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenSkipped() {
        final var shard = 0;

        final var changeContext = deleteMatchmakerAssignmentOperation.deleteMatchmakerAssignment(shard,
                matchmakerId(),
                matchmakerAssignmentId());
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_ASSIGNMENT_DELETED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerAssignmentId() {
        return generateIdOperation.generateId();
    }
}