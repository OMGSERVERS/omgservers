package com.omgservers.module.user.impl.operation.deleteObject;

import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.user.factory.ObjectModelFactory;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.upsertObject.UpsertObjectOperation;
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
class DeleteObjectOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeleteObjectOperation deleteObjectOperation;

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
    void givenUserPlayer_whenDeleteObject_thenDeleted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object = objectModelFactory.create(user.getId(), player.getId(), UUID.randomUUID().toString(), new byte[5]);
        final var id = object.getId();
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, user.getId(), object);

        assertTrue(deleteObjectOperation.deleteObject(TIMEOUT, pgPool, shard, user.getId(), player.getId(), id));
    }

    @Test
    void givenUnknownIds_whenDeleteObject_thenSkip() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var playerId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        assertFalse(deleteObjectOperation.deleteObject(TIMEOUT, pgPool, shard, userId, playerId, id));
    }

    long stageId() {
        return generateIdOperation.generateId();
    }
}