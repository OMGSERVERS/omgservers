package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.service.factory.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatch.UpsertMatchmakerMatchOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmakerMatchClient.UpsertMatchmakerMatchClientOperation;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.service.module.matchmaker.operation.testInterface.DeleteMatchmakerMatchClientOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteMatchmakerMatchRuntimeRefOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteMatchmakerMatchClientOperationTestInterface deleteMatchClientOperation;

    @Inject
    UpsertMatchmakerMatchClientOperation upsertMatchmakerMatchClientOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    @Inject
    MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchClient_whenDeleteMatchClient_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);
        final var match = matchmakerMatchModelFactory.create(matchmaker.getId(), new MatchmakerMatchConfigModel());
        upsertMatchmakerMatchOperation.upsertMatchmakerMatch(TIMEOUT, pgPool, shard, match);
        final var matchClient =
                matchmakerMatchClientModelFactory.create(matchmaker.getId(), match.getId(), userId(), clientId(), groupName(),
                        new MatchmakerMatchClientConfigModel());
        upsertMatchmakerMatchClientOperation.upsertMatchmakerMatchClient(TIMEOUT, pgPool, shard, matchClient);

        final var changeContext = deleteMatchClientOperation
                .deleteMatchmakerMatchClient(shard, matchmaker.getId(), matchClient.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_MATCH_CLIENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchClient_thenFalse() {
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

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long requestId() {
        return generateIdOperation.generateId();
    }

    String groupName() {
        return "group-" + UUID.randomUUID();
    }
}