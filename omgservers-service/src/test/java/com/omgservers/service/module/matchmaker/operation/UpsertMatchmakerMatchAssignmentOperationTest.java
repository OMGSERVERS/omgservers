package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchAssignmentOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertMatchmakerMatchAssignmentOperationTest extends BaseTestClass {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperationTestInterface upsertMatchmakerMatchOperation;

    @Inject
    UpsertMatchmakerMatchAssignmentOperationTestInterface upsertMatchmakerMatchAssignmentOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerMatchAssignment_whenExecute_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerMatch =
                matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigDto());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchAssignmentConfigDto());
        final var changeContext = upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerMatchAssignment_whenExecute_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerMatch =
                matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigDto());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchAssignmentConfigDto());
        upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment);

        final var changeContext = upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory.create(matchmakerId(),
                matchId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchAssignmentConfigDto());

        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment));
    }

    @Test
    void givenMatchmakerMatchAssignment_whenUpsertMatchmakerMatchAssignment_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerMatch =
                matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigDto());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchmakerMatchAssignment1 = matchmakerMatchAssignmentModelFactory
                .create(matchmaker.getId(),
                        matchmakerMatch.getId(),
                        userId(),
                        clientId(),
                        groupName(),
                        new MatchmakerMatchAssignmentConfigDto());
        upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment1);

        final var matchmakerMatchAssignment2 = matchmakerMatchAssignmentModelFactory
                .create(matchmaker.getId(),
                        matchmakerMatch.getId(),
                        userId(),
                        clientId(),
                        groupName(),
                        new MatchmakerMatchAssignmentConfigDto(),
                        matchmakerMatchAssignment1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerMatchAssignmentOperation.execute(shard, matchmakerMatchAssignment2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
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

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }

    String groupName() {
        return "group-" + UUID.randomUUID();
    }
}