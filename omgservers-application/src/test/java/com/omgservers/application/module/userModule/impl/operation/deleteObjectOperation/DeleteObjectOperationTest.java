package com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation;

import com.omgservers.application.factory.ObjectModelFactory;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.application.factory.PlayerModelFactory;
import com.omgservers.application.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation.UpsertObjectOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class DeleteObjectOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

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
        final var object = objectModelFactory.create(player.getId(), UUID.randomUUID().toString(), new byte[5]);
        final var id = object.getId();
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object);

        assertTrue(deleteObjectOperation.deleteObject(TIMEOUT, pgPool, shard, id));
    }

    @Test
    void givenUnknownUuid_whenDeleteObject_thenSkip() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertFalse(deleteObjectOperation.deleteObject(TIMEOUT, pgPool, shard, id));
    }

    long stageId() {
        return generateIdOperation.generateId();
    }
}