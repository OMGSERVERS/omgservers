package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.event.EventQualifierEnum;
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
class DeleteMatchmakerOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteMatchmakerOperationTestInterface deleteMatchmakerOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmaker_whenDeleteMatchmaker_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var changeContext = deleteMatchmakerOperation.deleteMatchmaker(shard, matchmaker.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.MATCHMAKER_DELETED));
    }

    @Test
    void givenUnknownUuid_whenDeleteMatchmaker_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteMatchmakerOperation.deleteMatchmaker(shard, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.MATCHMAKER_DELETED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}