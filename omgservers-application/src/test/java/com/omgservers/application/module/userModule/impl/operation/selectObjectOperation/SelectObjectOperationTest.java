package com.omgservers.application.module.userModule.impl.operation.selectObjectOperation;

import com.omgservers.application.module.userModule.model.object.ObjectModel;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectObjectOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectObjectOperation selectObjectOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertObjectOperation upsertObjectOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenObject_whenSelectObject_thenSelected() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var playerUuid = player.getUuid();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object1 = ObjectModel.create(player.getUuid(), UUID.randomUUID().toString(), new byte[5]);
        final var objectName = object1.getName();
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object1);

        final var object2 = selectObjectOperation.selectObject(TIMEOUT, pgPool, shard, playerUuid, objectName);
        assertEquals(object1, object2);
    }

    @Test
    void givenUnknownUuids_whenSelectObject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var playerUuid = UUID.randomUUID();
        final var objectName = UUID.randomUUID().toString();

        assertThrows(ServerSideNotFoundException.class, () -> selectObjectOperation
                .selectObject(TIMEOUT, pgPool, shard, playerUuid, objectName));
    }
}