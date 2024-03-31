package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchClientOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerMatchOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertMatchmakerMatchClientOperationTest extends Assertions {

    @Inject
    UpsertMatchmakerOperationTestInterface upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperationTestInterface upsertMatchmakerMatchOperation;

    @Inject
    UpsertMatchmakerMatchClientOperationTestInterface upsertMatchmakerMatchClientOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenMatchmakerMatchClient_whenUpsertMatchmakerMatchClient_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerMatch =
                matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigModel());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchClient = matchmakerMatchClientModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchClientConfigModel());
        final var changeContext = upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchClient);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenMatchmakerMatchClient_whenUpsertMatchmakerMatchClient_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerMatch =
                matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigModel());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchClient = matchmakerMatchClientModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchClientConfigModel());
        upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchClient);

        final var changeContext = upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchClient);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenUpsertMatchmakerMatchClient_thenException() {
        final var shard = 0;
        final var matchClient = matchmakerMatchClientModelFactory.create(matchmakerId(),
                matchId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchClientConfigModel());

        assertThrows(ServerSideBadRequestException.class, () ->
                upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchClient));
    }

    @Test
    void givenMatchmakerRequest_whenUpsertMatchmakerRequest_thenIdempotencyViolation() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);

        final var matchmakerMatch =
                matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigModel());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);

        final var matchClient1 = matchmakerMatchClientModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchClientConfigModel());
        upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchClient1);

        final var matchClient2 = matchmakerMatchClientModelFactory.create(matchmaker.getId(),
                matchmakerMatch.getId(),
                userId(),
                clientId(),
                groupName(),
                new MatchmakerMatchClientConfigModel(),
                matchClient1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchClient2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
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