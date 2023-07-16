package com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation;

import com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation.InsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.insertRequestOperation.InsertRequestOperation;
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
class DeleteRequestOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteRequestOperation deleteRequestOperation;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    InsertRequestOperation insertRequestOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenRequest_whenDeleteRequest_thenDeleted() {
        final var shard = 0;
        final var matchmaker = MatchmakerModel.create(tenantUuid(), stageUuid());
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var requestConfig = RequestConfigModel.create(userUuid(), clientUuid(), tenantUuid(), stageUuid(), modeName(), poolName());
        final var request = RequestModel.create(matchmaker.getUuid(), requestConfig);
        insertRequestOperation.insertRequest(TIMEOUT, pgPool, shard, request);

        assertTrue(deleteRequestOperation.deleteRequest(TIMEOUT, pgPool, shard, request.getUuid()));
    }

    @Test
    void givenUnknownUuid_whenDeleteTenant_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteRequestOperation.deleteRequest(TIMEOUT, pgPool, shard, uuid));
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

    String poolName() {
        return "pool-" + UUID.randomUUID();
    }
}