package com.omgservers.application.module.userModule.impl.operation.deleteClientOperation;

import com.omgservers.application.factory.ClientModelFactory;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.application.factory.PlayerModelFactory;
import com.omgservers.application.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertClientOperation.UpsertClientOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.net.URI;

@Slf4j
@QuarkusTest
class DeleteClientOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteClientOperation deleteClientOperation;

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
    void givenUserPlayerClient_whenDeleteClient_thenDeleted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client = clientModelFactory.create(player.getId(), URI.create("http://localhost:8080"), connectionId());
        final var clientId = client.getId();
        insertClientOperation.upsertClient(TIMEOUT, pgPool, shard, client);

        assertTrue(deleteClientOperation.deleteClient(TIMEOUT, pgPool, shard, clientId));
    }

    @Test
    void givenUnknownUuid_whenDeleteClient_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteClientOperation.deleteClient(TIMEOUT, pgPool, shard, id));
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }

    Long connectionId() {
        return generateIdOperation.generateId();
    }
}