package com.omgservers.module.matchmaker.impl.operation.upsertRequest;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.matchmaker.factory.RequestModelFactory;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.module.matchmaker.impl.operation.UpsertRequestOperationTestInterface;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertRequestOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertRequestOperationTestInterface upsertRequestOperation;

    @Inject
    UpsertMatchmakerOperation upsertMatchmakerOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    RequestModelFactory requestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRequest_whenUpsertRequest_thenInserted() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create(PlayerAttributesModel.create());
        final var matchmakerRequest = requestModelFactory.create(matchmaker.getId(), userId(), clientId(), modeName(), matchmakerRequestConfig);
        final var changeContext = upsertRequestOperation.upsertRequest(shard, matchmakerRequest);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenRequest_whenUpsertRequestAgain_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create(PlayerAttributesModel.create());
        final var matchmakerRequest = requestModelFactory.create(matchmaker.getId(), userId(), clientId(), modeName(), matchmakerRequestConfig);
        upsertRequestOperation.upsertRequest(shard, matchmakerRequest);

        final var changeContext = upsertRequestOperation.upsertRequest(shard, matchmakerRequest);
        assertFalse(changeContext.getResult());
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