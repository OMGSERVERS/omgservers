package com.omgservers.application.module.userModule.impl.operation.selectClientOperation;

import com.omgservers.application.module.userModule.model.client.ClientModelFactory;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModelFactory;
import com.omgservers.application.module.userModule.model.user.UserModelFactory;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.userModule.impl.operation.insertClientOperation.InsertClientOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.net.URI;

@Slf4j
@QuarkusTest
class SelectClientOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectClientOperation selectClientOperation;

    @Inject
    InsertClientOperation insertClientOperation;

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
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client1 = clientModelFactory.create(player.getId(), URI.create("http://localhost:8080"), connectionId());
        final var clientUuid = client1.getId();
        insertClientOperation.insertClient(TIMEOUT, pgPool, shard, client1);

        final var client2 = selectClientOperation.selectClient(TIMEOUT, pgPool, shard, clientUuid);
        assertEquals(client1, client2);
    }

    @Test
    void givenUnknown_whenSelectClient_thenServerSideNotFoundException() {
        final var shard = 0;
        final var clientId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectClientOperation
                .selectClient(TIMEOUT, pgPool, shard, clientId));
    }

    long stageId() {
        return generateIdOperation.generateId();
    }

    long connectionId() {
        return generateIdOperation.generateId();
    }
}