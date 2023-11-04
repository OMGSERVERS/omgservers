package com.omgservers.service.module.user.operation;

import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.service.module.user.impl.operation.upsertUser.UpsertUserOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertPlayerOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenUser_whenUpsertPlayer_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);

        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        assertTrue(upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player));
    }

    @Test
    void givenUserAndPlayer_whenUpsertPlayer_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        assertFalse(upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}