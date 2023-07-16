package com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation;

import com.omgservers.application.module.userModule.model.object.ObjectModel;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.UUID;

@Slf4j
@QuarkusTest
class UpsertObjectOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    UpsertObjectOperation upsertObjectOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenUserPlayer_whenUpsertObject_thenInserted() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var object = ObjectModel.create(playerUuid, UUID.randomUUID().toString(), new byte[5]);
        assertTrue(upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object));
    }

    @Test
    void givenUserPlayerObject_whenUpsertPlayer_thenUpdated() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object = ObjectModel.create(playerUuid, UUID.randomUUID().toString(), new byte[]{0, 1, 2, 3, 4, 5, 6, 7});
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object);

        assertFalse(upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object));
    }
}