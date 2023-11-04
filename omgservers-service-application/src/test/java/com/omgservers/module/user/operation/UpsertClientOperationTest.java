package com.omgservers.module.user.operation;

import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.factory.ClientModelFactory;
import com.omgservers.factory.PlayerModelFactory;
import com.omgservers.factory.UserModelFactory;
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
class UpsertClientOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

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
    void givenUserPlayer_whenUpsertClient_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var client =
                clientModelFactory.create(user.getId(), playerId, URI.create("http://localhost:8080"), connectionId(),
                        versionId(), defaultMatchmakerId(), defaultRuntimeId());
        insertClientOperation.upsertClient(TIMEOUT, pgPool, shard, client);
    }

    @Test
    void givenClient_whenUpsertClientAgain_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client =
                clientModelFactory.create(user.getId(), playerId, URI.create("http://localhost:8080"), connectionId(),
                        versionId(), defaultMatchmakerId(), defaultRuntimeId());
        insertClientOperation.upsertClient(TIMEOUT, pgPool, shard, client);

        assertFalse(insertClientOperation.upsertClient(TIMEOUT, pgPool, shard, client));
    }

    @Test
    void givenUnknownIds_whenUpsertClient_thenException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var client =
                clientModelFactory.create(userId, playerId(), URI.create("http://localhost:8080"), connectionId(),
                        versionId(), defaultMatchmakerId(), defaultRuntimeId());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                insertClientOperation.upsertClient(TIMEOUT, pgPool, shard, client));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    long playerId() {
        return generateIdOperation.generateId();
    }

    long connectionId() {
        return generateIdOperation.generateId();
    }

    long stageId() {
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