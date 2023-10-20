package com.omgservers.module.user.impl.operation.selectClient;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.user.factory.ClientModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.upsertClient.UpsertClientOperation;
import com.omgservers.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
    SelectClientOperation selectClientOperation;

    @Inject
    UpsertClientOperation insertClientOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

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
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client1 = clientModelFactory.create(user.getId(), player.getId(), URI.create("http://localhost:8080"),
                connectionId(), versionId(), defaultRuntimeId());
        final var clientId = client1.getId();
        insertClientOperation.upsertClient(TIMEOUT, pgPool, shard, client1);

        final var client2 = selectClientOperation.selectClient(TIMEOUT, pgPool, shard, user.getId(), clientId);
        assertEquals(client1, client2);
    }

    @Test
    void givenUnknownIds_whenSelectClient_thenServerSideNotFoundException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var clientId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectClientOperation
                .selectClient(TIMEOUT, pgPool, shard, userId, clientId));
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

    long defaultRuntimeId() {
        return generateIdOperation.generateId();
    }
}