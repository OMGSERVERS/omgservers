package com.omgservers.application.module.matchmakerModule.impl.operation.selectMatchmakerOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectMatchmakerOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectMatchmakerOperation selectMatchmakerOperation;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmaker_whenSelectMatchmaker_thenSelected() {
        final var shard = 0;
        final var matchmaker1 = MatchmakerModel.create(tenantUuid(), stageUuid());
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker1);

        final var matchmaker2 = selectMatchmakerOperation.selectMatchmaker(TIMEOUT, pgPool, shard, matchmaker1.getUuid());
        assertEquals(matchmaker1, matchmaker2);
    }

    @Test
    void givenUnknownUuid_whenSelectMatchmaker_then() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerOperation
                .selectMatchmaker(TIMEOUT, pgPool, shard, uuid));
        log.info("Exception: {}", exception.getMessage());
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}