package com.omgservers.module.matchmaker.impl.operation.selectRequest;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.factory.MatchmakerModelFactory;
import com.omgservers.factory.RequestModelFactory;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.module.matchmaker.impl.operation.upsertRequest.UpsertRequestOperation;
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
class SelectRequestOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectRequestOperation selectRequestOperation;

    @Inject
    UpsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    UpsertRequestOperation upsertRequestOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    RequestModelFactory requestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmakerRequest_whenSelectMatchmakerRequest_thenSelected() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create();
        final var matchmakerRequest1 = requestModelFactory.create(matchmaker.getId(), userId(), clientId(), modeName(), matchmakerRequestConfig);
        upsertRequestOperation.upsertRequest(TIMEOUT, pgPool, shard, matchmakerRequest1);

        final var matchmakerRequest2 = selectRequestOperation.selectRequest(TIMEOUT, pgPool, shard, matchmakerRequest1.getId());
        assertEquals(matchmakerRequest1, matchmakerRequest2);
    }

    @Test
    void givenUnknownUuid_whenSelectMatchmakerRequest_then() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        final var exception = assertThrows(ServerSideNotFoundException.class, () -> selectRequestOperation
                .selectRequest(TIMEOUT, pgPool, shard, id));
        log.info("Exception: {}", exception.getMessage());
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