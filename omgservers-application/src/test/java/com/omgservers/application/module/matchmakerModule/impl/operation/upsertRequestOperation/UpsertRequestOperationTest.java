package com.omgservers.application.module.matchmakerModule.impl.operation.upsertRequestOperation;

import com.omgservers.application.module.matchmakerModule.impl.operation.upsertMatchmakerOperation.UpsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModelFactory;
import com.omgservers.application.module.matchmakerModule.model.request.RequestConfigModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModelFactory;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertRequestOperation upsertRequestOperation;

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

        final var matchmakerRequestConfig = RequestConfigModel.create(userId(), clientId(), tenantId(), stageId(), modeName());
        final var matchmakerRequest = requestModelFactory.create(matchmaker.getId(), matchmakerRequestConfig);
        assertTrue(upsertRequestOperation.upsertRequest(TIMEOUT, pgPool, shard, matchmakerRequest));
    }

    @Test
    void givenRequest_whenUpsertRequestAgain_thenUpdated() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create(userId(), clientId(), tenantId(), stageId(), modeName());
        final var matchmakerRequest = requestModelFactory.create(matchmaker.getId(), matchmakerRequestConfig);
        upsertRequestOperation.upsertRequest(TIMEOUT, pgPool, shard, matchmakerRequest);

        assertFalse(upsertRequestOperation.upsertRequest(TIMEOUT, pgPool, shard, matchmakerRequest));
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