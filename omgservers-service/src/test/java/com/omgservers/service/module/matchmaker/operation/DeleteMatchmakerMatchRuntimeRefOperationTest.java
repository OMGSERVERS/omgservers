package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.operation.testInterface.DeleteMatchmakerMatchClientOperationTestInterface;
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
class DeleteMatchmakerMatchRuntimeRefOperationTest extends Assertions {

    @Inject
    DeleteMatchmakerMatchClientOperationTestInterface deleteMatchClientOperation;

    @Inject
    UpsertMatchmakerMatchClientOperationTestInterface upsertMatchmakerMatchClientOperation;

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
    MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;

    @Test
    void givenMatchClient_whenDeleteMatchmakerMatchClient_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), versionId());
        upsertMatchmakerOperation.upsertMatchmaker(shard, matchmaker);
        final var matchmakerMatch = matchmakerMatchModelFactory
                .create(matchmaker.getId(), new MatchmakerMatchConfigModel());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(shard, matchmakerMatch);
        final var matchmakerMatchClient = matchmakerMatchClientModelFactory
                .create(matchmaker.getId(),
                        matchmakerMatch.getId(),
                        userId(),
                        clientId(),
                        groupName(),
                        new MatchmakerMatchClientConfigModel());
        upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(shard, matchmakerMatchClient);

        final var changeContext = deleteMatchClientOperation
                .deleteMatchmakerMatchClient(shard, matchmaker.getId(), matchmakerMatchClient.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_CLIENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchmakerMatchClient_thenSkip() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchClientOperation.deleteMatchmakerMatchClient(shard, matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_CLIENT_DELETED));
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