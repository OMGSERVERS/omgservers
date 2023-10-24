package com.omgservers.module.matchmaker.impl.operation.selectRequest;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.matchmaker.factory.RequestModelFactory;
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
class SelectMatchClientsByMatchmakerIdAndMatchIdOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectRequestOperation selectRequestOperation;

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
    void givenMatchmakerRequest_whenSelectMatchmakerRequest_thenSelected() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        insertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = RequestConfigModel.create(PlayerAttributesModel.create());
        final var matchmakerRequest1 = requestModelFactory.create(matchmaker.getId(), userId(), clientId(), modeName(),
                matchmakerRequestConfig);
        upsertRequestOperation.upsertRequest(shard, matchmakerRequest1);

        final var matchmakerRequest2 = selectRequestOperation.selectRequest(TIMEOUT, pgPool, shard, matchmaker.getId(),
                matchmakerRequest1.getId());
        assertEquals(matchmakerRequest1, matchmakerRequest2);
    }

    @Test
    void givenUnknownIds_whenSelectMatchmakerRequest_thenException() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectRequestOperation
                .selectRequest(TIMEOUT, pgPool, shard, matchmakerId, id));
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