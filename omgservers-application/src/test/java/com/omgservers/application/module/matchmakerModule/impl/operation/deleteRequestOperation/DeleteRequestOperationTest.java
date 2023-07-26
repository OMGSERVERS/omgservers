package com.omgservers.application.module.matchmakerModule.impl.operation.deleteRequestOperation;

import com.omgservers.application.module.matchmakerModule.impl.operation.insertMatchmakerOperation.InsertMatchmakerOperation;
import com.omgservers.application.module.matchmakerModule.impl.operation.insertRequestOperation.InsertRequestOperation;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModelFactory;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModelFactory;
import com.omgservers.application.module.matchmakerModule.model.request.RequestConfigModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
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
class DeleteRequestOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteRequestOperation deleteRequestOperation;

    @Inject
    InsertMatchmakerOperation insertMatchmakerOperation;

    @Inject
    InsertRequestOperation insertRequestOperation;

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
        insertMatchmakerOperation.insertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var requestConfig = RequestConfigModel.create(userId(), clientId(), tenantId(), stageId(), modeName());
        final var request = requestModelFactory.create(matchmaker.getId(), requestConfig);
        insertRequestOperation.insertRequest(TIMEOUT, pgPool, shard, request);

        assertTrue(deleteRequestOperation.deleteRequest(TIMEOUT, pgPool, shard, request.getId()));
    }

    @Test
    void givenUnknownUuid_whenDeleteTenant_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteRequestOperation.deleteRequest(TIMEOUT, pgPool, shard, id));
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