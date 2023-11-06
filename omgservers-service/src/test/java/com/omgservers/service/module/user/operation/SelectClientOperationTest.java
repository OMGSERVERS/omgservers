package com.omgservers.service.module.user.operation;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.service.module.user.operation.testInterface.SelectClientOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

@Slf4j
@QuarkusTest
class SelectClientOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectClientOperationTestInterface selectClientOperation;

    @Inject
    UpsertClientOperationTestInterface upsertClientOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    ClientModelFactory clientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenUserPlayerClient_whenSelectClient_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client1 = clientModelFactory.create(user.getId(), player.getId(), URI.create("http://localhost:8080"),
                connectionId(), versionId(), defaultMatchmakerId(), defaultRuntimeId());
        final var clientId = client1.getId();
        upsertClientOperation.upsertClient(shard, client1);

        final var client2 = selectClientOperation.selectClient(shard, user.getId(), clientId, false);
        assertEquals(client1, client2);
    }

    @Test
    void givenUnknownIds_whenSelectClient_thenException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var clientId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectClientOperation
                .selectClient(shard, userId, clientId, false));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    long stageId() {
        return generateIdOperation.generateId();
    }

    long connectionId() {
        return generateIdOperation.generateId();
    }

    long versionId() {
        return generateIdOperation.generateId();
    }

    long defaultMatchmakerId() {
        return generateIdOperation.generateId();
    }

    long defaultRuntimeId() {
        return generateIdOperation.generateId();
    }
}