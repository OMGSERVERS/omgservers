package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
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
class SelectMatchmakerOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectMatchmakerOperationTestInterface selectMatchmakerOperation;

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

        final var matchmaker2 = selectMatchmakerOperation.selectMatchmaker(shard, matchmaker1.getId(), false);
        assertEquals(matchmaker1, matchmaker2);
    }

    @Test
    void givenUnknownUuid_whenSelectMatchmaker_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerOperation
                .selectMatchmaker(shard, id, false));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}