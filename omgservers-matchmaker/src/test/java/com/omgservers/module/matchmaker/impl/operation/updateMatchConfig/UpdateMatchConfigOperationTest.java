package com.omgservers.module.matchmaker.impl.operation.updateMatchConfig;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.module.matchmaker.factory.MatchModelFactory;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.matchmaker.impl.operation.upsertMatch.UpsertMatchOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@Slf4j
@QuarkusTest
class UpdateMatchConfigOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpdateMatchConfigOperation updateMatchConfigOperation;

    @Inject
    UpsertMatchOperation upsertMatchOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchModelFactory matchModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatch_whenUpdateMatch_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);
        final var match = matchModelFactory.create(matchmaker.getId(), new MatchConfigModel());
        upsertMatchOperation.upsertMatch(TIMEOUT, pgPool, shard, match);

        match.setModified(Instant.now());
        match.setConfig(new MatchConfigModel());

        updateMatchConfigOperation.updateMatch(TIMEOUT, pgPool, shard, match.getMatchmakerId(), match.getId(), match.getConfig());
    }

    @Test
    void givenMatchWithUnknownId_whenUpdateMatch_thenException() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);
        final var match = matchModelFactory.create(matchmaker.getId(), new MatchConfigModel());
        upsertMatchOperation.upsertMatch(TIMEOUT, pgPool, shard, match);

        // Set unknown id
        match.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> updateMatchConfigOperation
                .updateMatch(TIMEOUT, pgPool, shard, match.getMatchmakerId(), match.getId(), match.getConfig()));
        log.info("Exception: {}", exception.getMessage());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}