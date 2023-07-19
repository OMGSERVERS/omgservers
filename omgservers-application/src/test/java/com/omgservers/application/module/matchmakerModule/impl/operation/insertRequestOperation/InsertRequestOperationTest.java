package com.omgservers.application.module.matchmakerModule.impl.operation.insertRequestOperation;

import com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation.InsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestConfigModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class InsertRequestOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertRequestOperation insertRequestOperation;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    PgPool pgPool;

    @Test
    void whenInsertMatchmakerRequest() {
        final var shard = 0;
        final var matchmaker = MatchmakerModel.create(tenantUuid(), stageUuid());
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create(userUuid(), clientUuid(), tenantUuid(), stageUuid(), modeName());
        final var matchmakerRequest = RequestModel.create(matchmaker.getUuid(), matchmakerRequestConfig);
        insertRequestOperation.insertRequest(TIMEOUT, pgPool, shard, matchmakerRequest);
    }

    UUID userUuid() {
        return UUID.randomUUID();
    }

    UUID clientUuid() {
        return UUID.randomUUID();
    }

    UUID tenantUuid() {
        return UUID.randomUUID();
    }

    UUID stageUuid() {
        return UUID.randomUUID();
    }

    String modeName() {
        return "mode-" + UUID.randomUUID();
    }
}