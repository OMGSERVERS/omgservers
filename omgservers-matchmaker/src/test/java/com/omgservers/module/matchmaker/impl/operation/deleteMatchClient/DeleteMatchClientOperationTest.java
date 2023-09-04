package com.omgservers.module.matchmaker.impl.operation.deleteMatchClient;

import com.omgservers.model.match.MatchConfigModel;
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

@Slf4j
@QuarkusTest
class DeleteMatchClientOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteMatchClientOperation deleteMatchClientOperation;

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
        final var matchClient = matchClientModelFactory.create(matchmaker.getId(), match.getId(), userId(), clientId());
        upsertMatchClientOperation.upsertMatchClient(TIMEOUT, pgPool, shard, matchClient);

        assertTrue(deleteMatchClientOperation.deleteMatchClient(TIMEOUT, pgPool, shard, matchmaker.getId(), matchClient.getId()));
    }

    @Test
    void givenUnknownIds_whenDeleteMatchClient_thenFalse() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        assertFalse(deleteMatchClientOperation.deleteMatchClient(TIMEOUT, pgPool, shard, matchmakerId, id));
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
}