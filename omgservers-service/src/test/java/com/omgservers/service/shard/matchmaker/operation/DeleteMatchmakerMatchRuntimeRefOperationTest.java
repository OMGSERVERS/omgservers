package com.omgservers.service.shard.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.shard.matchmaker.operation.testInterface.DeleteMatchmakerMatchAssignmentOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerMatchAssignmentOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerMatchOperationTestInterface;
import com.omgservers.service.shard.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteMatchmakerMatchRuntimeRefOperationTest extends BaseTestClass {

    @Inject
    DeleteMatchmakerMatchAssignmentOperationTestInterface deleteMatchmakerMatchAssignmentOperation;

    @Inject
    UpsertMatchmakerMatchAssignmentOperationTestInterface upsertMatchmakerMatchAssignmentOperation;

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperationTestInterface upsertMatchmakerMatchOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;

    @Test
    void givenMatchmakerMatchAssignment_whenDeleteMatchmakerMatchAssignment_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);
        final var matchmakerMatch = matchmakerMatchModelFactory
                .create(matchmaker.getId(), new MatchmakerMatchConfigDto());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);
        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory
                .create(matchmaker.getId(),
                        matchmakerMatch.getId(),
                        userId(),
                        clientId(),
                        groupName(),
                        new MatchmakerMatchAssignmentConfigDto());
        upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment);

        final var changeContext = deleteMatchmakerMatchAssignmentOperation
                .execute(shard, matchmaker.getId(), matchmakerMatchAssignment.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchmakerMatchAssignment_thenSkip() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchmakerMatchAssignmentOperation.execute(shard,
                matchmakerId,
                id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED));
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long requestId() {
        return generateIdOperation.generateId();
    }

    String groupName() {
        return "group-" + UUID.randomUUID();
    }
}