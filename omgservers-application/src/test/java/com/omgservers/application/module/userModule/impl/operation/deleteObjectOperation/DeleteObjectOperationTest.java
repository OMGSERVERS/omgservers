package com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation;

import com.omgservers.application.module.userModule.model.object.ObjectModel;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation.UpsertObjectOperation;
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
    PgPool pgPool;

    @Test
    void givenUserPlayer_whenDeleteObject_thenDeleted() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object = ObjectModel.create(player.getUuid(), UUID.randomUUID().toString(), new byte[5]);
        final var uuid = object.getUuid();
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object);

        assertTrue(deleteObjectOperation.deleteObject(TIMEOUT, pgPool, shard, uuid));
    }

    @Test
    void givenUnknownUuid_whenDeleteObject_thenSkip() {
        final var shard = 0;
        final var uuid = UUID.randomUUID();

        assertFalse(deleteObjectOperation.deleteObject(TIMEOUT, pgPool, shard, uuid));
    }
}