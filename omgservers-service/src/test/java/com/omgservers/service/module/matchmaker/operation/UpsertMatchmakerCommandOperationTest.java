package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.CloseMatchMatchmakerCommandBodyDto;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerCommandModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerCommandOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertMatchmakerCommandOperationTest extends BaseTestClass {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerCommandOperationTestInterface upsertMatchmakerCommandOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerCommand_whenExecute_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerCommandBody = new CloseMatchMatchmakerCommandBodyDto(matchId());
        final var matchmakerCommand = matchmakerCommandModelFactory.create(matchmaker.getId(),
                matchmakerCommandBody);

        final var changeContext = upsertMatchmakerCommandOperation.upsertMatchmakerCommand(shard, matchmakerCommand);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerCommand_whenExecuteAgain_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerCommandBody = new CloseMatchMatchmakerCommandBodyDto(matchId());
        final var matchmakerCommand = matchmakerCommandModelFactory.create(matchmaker.getId(),
                matchmakerCommandBody);
        upsertMatchmakerCommandOperation.upsertMatchmakerCommand(shard, matchmakerCommand);

        final var changeContext = upsertMatchmakerCommandOperation.upsertMatchmakerCommand(shard, matchmakerCommand);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var matchmakerCommandBody = new CloseMatchMatchmakerCommandBodyDto(matchId());
        final var matchmakerCommand = matchmakerCommandModelFactory.create(matchmakerId(),
                matchmakerCommandBody);
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerCommandOperation.upsertMatchmakerCommand(shard, matchmakerCommand));
    }

    @Test
    void givenMatchmakerCommand_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerCommandBody1 = new CloseMatchMatchmakerCommandBodyDto(matchId());
        final var matchmakerCommand1 = matchmakerCommandModelFactory.create(matchmaker.getId(),
                matchmakerCommandBody1);
        upsertMatchmakerCommandOperation.upsertMatchmakerCommand(shard, matchmakerCommand1);

        final var matchmakerCommandBody2 = new CloseMatchMatchmakerCommandBodyDto(matchId());
        final var matchmakerCommand2 = matchmakerCommandModelFactory.create(matchmaker.getId(),
                matchmakerCommandBody2,
                matchmakerCommand1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerCommandOperation.upsertMatchmakerCommand(shard, matchmakerCommand2));
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
}