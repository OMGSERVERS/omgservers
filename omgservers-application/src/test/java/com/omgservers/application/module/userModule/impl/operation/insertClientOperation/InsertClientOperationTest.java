package com.omgservers.application.module.userModule.impl.operation.insertClientOperation;

import com.omgservers.application.module.userModule.model.client.ClientModel;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.net.URI;
import java.util.UUID;

@Slf4j
@QuarkusTest
class InsertClientOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertClientOperation insertClientOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenUserPlayer_whenInsertClient_thenInserted() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var client = ClientModel.create(playerUuid, URI.create("http://localhost:8080"), UUID.randomUUID());
        insertClientOperation.insertClient(TIMEOUT, pgPool, shard, client);
    }

    @Test
    void givenClient_whenInsertClientAgain_thenServerSideConflictException() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client = ClientModel.create(playerUuid, URI.create("http://localhost:8080"), UUID.randomUUID());
        insertClientOperation.insertClient(TIMEOUT, pgPool, shard, client);

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                insertClientOperation.insertClient(TIMEOUT, pgPool, shard, client));
        log.info("Exception: {}", exception.getMessage());
    }

    @Test
    void whenInsertClientWithUnknownPlayerUuid_thenServerSideNotFoundException() {
        final var shard = 0;
        final var client = ClientModel.create(UUID.randomUUID(), URI.create("http://localhost:8080"), UUID.randomUUID());
        final var exception = assertThrows(ServerSideNotFoundException.class, () ->
                insertClientOperation.insertClient(TIMEOUT, pgPool, shard, client));
        log.info("Exception: {}", exception.getMessage());
    }
}