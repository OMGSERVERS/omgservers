package com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation;

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
class InsertMatchmakerOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    PgPool pgPool;

    @Test
    void whenInsertMatchmaker() {
        final var shard = 0;
        final var matchmaker = MatchmakerModel.create(tenantUuid(), stageUuid());
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }
}