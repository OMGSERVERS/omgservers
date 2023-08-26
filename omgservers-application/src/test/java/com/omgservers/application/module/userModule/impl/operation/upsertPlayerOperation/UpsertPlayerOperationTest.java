package com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation;

import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.application.factory.PlayerModelFactory;
import com.omgservers.application.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class UpsertPlayerOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

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

        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        assertTrue(upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player));
    }

    @Test
    void givenUserAndPlayer_whenUpsertPlayer_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        assertFalse(upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player));
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}