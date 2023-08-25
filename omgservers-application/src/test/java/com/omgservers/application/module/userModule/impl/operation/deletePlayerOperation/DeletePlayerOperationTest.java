package com.omgservers.application.module.userModule.impl.operation.deletePlayerOperation;

import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.base.factory.PlayerModelFactory;
import com.omgservers.base.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class DeletePlayerOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeletePlayerOperation deletePlayerOperation;

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
    void givenUserPlayer_whenDeletePlayer_thenDeleted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        final var id = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        assertTrue(deletePlayerOperation.deletePlayer(TIMEOUT, pgPool, shard, id));
    }

    @Test
    void givenUnknownUuid_whenDeletePlayer_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deletePlayerOperation.deletePlayer(TIMEOUT, pgPool, shard, id));
    }

    long stageId() {
        return generateIdOperation.generateId();
    }
}