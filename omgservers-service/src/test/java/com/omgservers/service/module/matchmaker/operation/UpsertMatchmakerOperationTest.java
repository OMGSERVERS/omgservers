package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class
UpsertMatchmakerOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertMatchmakerOperation upsertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmaker_whenUpsertMatchmaker_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        assertTrue(upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker));
    }

    @Test
    void givenMatchmaker_whenUpsertMatchmakerAgain_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        assertFalse(upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}