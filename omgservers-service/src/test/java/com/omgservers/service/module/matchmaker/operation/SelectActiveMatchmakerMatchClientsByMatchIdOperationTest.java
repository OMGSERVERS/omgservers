package com.omgservers.service.module.matchmaker.operation;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.MatchmakerRequestConfigModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.MatchmakerModelFactory;
import com.omgservers.service.factory.MatchmakerRequestModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
import com.omgservers.service.module.matchmaker.operation.testInterface.SelectMatchmakerRequestOperationTestInterface;
import com.omgservers.service.module.matchmaker.operation.testInterface.UpsertMatchmakerRequestOperationTestInterface;
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
class SelectActiveMatchmakerMatchClientsByMatchIdOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertMatchmakerOperation upsertMatchmakerOperation;

    @Inject
    UpsertMatchmakerRequestOperationTestInterface upsertMatchmakerRequestOperation;

    @Inject
    SelectMatchmakerRequestOperationTestInterface selectMatchmakerRequestOperation;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    MatchmakerRequestModelFactory matchmakerRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenMatchmakerRequest_whenSelectMatchmakerRequest_thenSelected() {
        final var shard = 0;
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        upsertMatchmakerOperation.upsertMatchmaker(TIMEOUT, pgPool, shard, matchmaker);

        final var matchmakerRequestConfig = MatchmakerRequestConfigModel.create(PlayerAttributesModel.create());
        final var matchmakerRequest1 = matchmakerRequestModelFactory
                .create(matchmaker.getId(), userId(), clientId(), modeName(), matchmakerRequestConfig);
        upsertMatchmakerRequestOperation.upsertMatchmakerRequest(shard, matchmakerRequest1);

        final var matchmakerRequest2 = selectMatchmakerRequestOperation.selectMatchmakerRequest(shard,
                matchmaker.getId(),
                matchmakerRequest1.getId());
        assertEquals(matchmakerRequest1, matchmakerRequest2);
    }

    @Test
    void givenUnknownIds_whenSelectMatchmakerRequest_thenException() {
        final var shard = 0;
        final var matchmakerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectMatchmakerRequestOperation
                .selectMatchmakerRequest(shard, matchmakerId, id));
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