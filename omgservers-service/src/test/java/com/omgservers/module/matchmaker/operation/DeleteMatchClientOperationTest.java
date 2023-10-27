package com.omgservers.module.matchmaker.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.matchClient.MatchClientConfigModel;
import com.omgservers.module.matchmaker.factory.MatchClientModelFactory;
import com.omgservers.module.matchmaker.factory.MatchModelFactory;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchClient.UpsertMatchClientOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteMatchClientOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteMatchClientOperationTestInterface deleteMatchClientOperation;

    @Inject
    UpsertMatchClientOperation upsertMatchClientOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    UpsertMatchOperation upsertMatchOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchModelFactory matchModelFactory;

    @Inject
    MatchClientModelFactory matchClientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchClient_whenDeleteMatchClient_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);
        final var match = matchModelFactory.create(matchmaker.getId(), new MatchConfigModel());
        upsertMatchOperation.upsertMatch(TIMEOUT, pgPool, shard, match);
        final var matchClient =
                matchClientModelFactory.create(matchmaker.getId(), match.getId(), userId(), clientId(), groupName(),
                        new MatchClientConfigModel());
        upsertMatchClientOperation.upsertMatchClient(TIMEOUT, pgPool, shard, matchClient);

        final var changeContext = deleteMatchClientOperation
                .deleteMatchClient(shard, matchmaker.getId(), matchClient.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCH_CLIENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchClient_thenFalse() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchClientOperation.deleteMatchClient(shard, matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCH_CLIENT_DELETED));
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