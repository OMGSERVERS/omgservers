package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.factory.RequestModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteRequestOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteRequestOperationTestInterface deleteRequestOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    UpsertRequestOperationTestInterface upsertRequestOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    RequestModelFactory requestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRequest_whenDeleteRequest_thenDeleted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var requestConfig = RequestConfigModel.create(PlayerAttributesModel.create());
        final var request =
                requestModelFactory.create(matchmaker.getId(), userId(), clientId(), modeName(), requestConfig);
        upsertRequestOperation.upsertRequest(shard, request);

        final var changeContext = deleteRequestOperation.deleteRequest(shard, matchmaker.getId(), request.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.REQUEST_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteRequest_thenFalse() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deleteRequestOperation.deleteRequest(shard, matchmakerId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.REQUEST_DELETED));
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

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}