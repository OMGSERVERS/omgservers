package com.omgservers.module.user.impl.operation.upsertObject;

import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.user.factory.ObjectModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertObjectOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertObjectOperation upsertObjectOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    ObjectModelFactory objectModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenObject_whenUpsertObject_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var object = objectModelFactory.create(user.getId(), playerId, UUID.randomUUID().toString(), new byte[5]);
        assertTrue(upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, user.getId(), object));
    }

    @Test
    void givenObject_whenUpsertObjectAgain_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object = objectModelFactory.create(user.getId(), playerId, UUID.randomUUID().toString(), new byte[]{0, 1, 2, 3, 4, 5, 6, 7});
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, user.getId(), object);

        assertFalse(upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, user.getId(), object));
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}