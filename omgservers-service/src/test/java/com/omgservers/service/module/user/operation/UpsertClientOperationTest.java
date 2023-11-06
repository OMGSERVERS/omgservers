package com.omgservers.service.module.user.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
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
class UpsertClientOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

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
    void givenClient_whenUpsertClient_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var client = clientModelFactory.create(user.getId(),
                playerId,
                URI.create("http://localhost:8080"),
                connectionId(),
                versionId(),
                defaultMatchmakerId(),
                defaultRuntimeId());
        final var changeContext = upsertClientOperation.upsertClient(shard, client);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.CLIENT_CREATED));
    }

    @Test
    void givenClient_whenUpsertClient_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var client = clientModelFactory.create(user.getId(),
                playerId,
                URI.create("http://localhost:8080"),
                connectionId(),
                versionId(),
                defaultMatchmakerId(),
                defaultRuntimeId());
        upsertClientOperation.upsertClient(shard, client);

        final var changeContext = upsertClientOperation.upsertClient(shard, client);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.CLIENT_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertClient_thenException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var client = clientModelFactory.create(userId,
                playerId(),
                URI.create("http://localhost:8080"),
                connectionId(),
                versionId(),
                defaultMatchmakerId(),
                defaultRuntimeId());
        assertThrows(ServerSideConflictException.class, () -> upsertClientOperation
                .upsertClient(shard, client));
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