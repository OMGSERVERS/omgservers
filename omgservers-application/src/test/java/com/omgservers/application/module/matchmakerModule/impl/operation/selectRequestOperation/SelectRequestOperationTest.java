package com.omgservers.application.module.matchmakerModule.impl.operation.selectRequestOperation;

import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectRequestOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectRequestOperation selectRequestOperation;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    InsertRequestOperation insertRequestOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmakerRequest_whenSelectMatchmakerRequest_thenSelected() {
        final var shard = 0;
        final var matchmaker = MatchmakerModel.create(tenantUuid(), stageUuid());
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create(userUuid(), clientUuid(), tenantUuid(), stageUuid(), modeName());
        final var matchmakerRequest1 = RequestModel.create(matchmaker.getUuid(), matchmakerRequestConfig);
        insertRequestOperation.insertRequest(TIMEOUT, pgPool, shard, matchmakerRequest1);

        final var matchmakerRequest2 = selectRequestOperation.selectRequest(TIMEOUT, pgPool, shard, matchmakerRequest1.getUuid());
        assertEquals(matchmakerRequest1, matchmakerRequest2);
    }

    @Test
    void givenUnknownUuid_whenSelectMatchmakerRequest_then() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectRequestOperation
                .selectRequest(TIMEOUT, pgPool, shard, uuid));
        log.info("Exception: {}", exception.getMessage());
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