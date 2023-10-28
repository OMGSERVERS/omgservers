package com.omgservers.module.matchmaker.operation;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.matchmaker.impl.operation.selectMatchmaker.SelectMatchmakerOperation;
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
class SelectMatchmakerOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectMatchmakerOperation selectMatchmakerOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmaker_whenSelectMatchmaker_thenSelected() {
        final var shard = 0;
        final var matchmaker1 = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker1);

        final var matchmaker2 = selectMatchmakerOperation.selectMatchmaker(TIMEOUT, pgPool, shard, matchmaker1.getId());
        assertEquals(matchmaker1, matchmaker2);
    }

    @Test
    void givenUnknownUuid_whenSelectMatchmaker_then() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerOperation
                .selectMatchmaker(TIMEOUT, pgPool, shard, id));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}