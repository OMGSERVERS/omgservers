package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerAssignmentOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertMatchmakerAssignmentOperationTest extends Assertions {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerAssignmentOperationTestInterface upsertMatchmakerAssignmentOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerAssignment_whenUpsertMatchmakerAssignment_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmaker.getId(), clientId());

        final var changeContext = upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard,
                matchmakerAssignment);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_ASSIGNMENT_CREATED));
    }

    @Test
    void givenMatchmakerAssignment_whenUpsertMatchmakerAssignment_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmaker.getId(), clientId());
        upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard, matchmakerAssignment);

        final var changeContext = upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard,
                matchmakerAssignment);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_ASSIGNMENT_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertMatchmakerAssignment_thenException() {
        final var shard = 0;
        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmakerId(), clientId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard, matchmakerAssignment));
    }

    @Test
    void givenMatchmakerAssignment_whenUpsertMatchmakerAssignment_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerAssignment1 = matchmakerAssignmentModelFactory.create(matchmaker.getId(), clientId());
        upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard, matchmakerAssignment1);

        final var matchmakerAssignment2 = matchmakerAssignmentModelFactory.create(matchmaker.getId(),
                clientId(),
                matchmakerAssignment1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerAssignmentOperation.upsertMatchmakerAssignment(shard, matchmakerAssignment2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
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
}