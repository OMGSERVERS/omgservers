package com.omgservers.application.module.matchmakerModule.impl.operation.deleteMatchmakerOperation;

import com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation.InsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteMatchmakerOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteMatchmakerOperation deleteMatchmakerOperation;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmaker_whenDeleteMatchmaker_thenDeleted() {
        final var shard = 0;
        final var matchmaker = MatchmakerModel.create(tenantUuid(), stageUuid());
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        assertTrue(deleteMatchmakerOperation.deleteMatchmaker(TIMEOUT, pgPool, shard, matchmaker.getUuid()));
    }

    @Test
    void givenUnknownUuid_whenDeleteMatchmaker_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteMatchmakerOperation.deleteMatchmaker(TIMEOUT, pgPool, shard, uuid));
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}