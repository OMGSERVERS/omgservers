package com.omgservers.application.module.userModule.impl.operation.selectObjectOperation;

import com.omgservers.application.module.userModule.model.client.ClientModelFactory;
import com.omgservers.application.module.userModule.model.object.ObjectModelFactory;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModelFactory;
import com.omgservers.application.module.userModule.model.user.UserModelFactory;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.userModule.impl.operation.upsertObjectOperation.UpsertObjectOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation.UpsertPlayerOperation;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
    void givenObject_whenSelectObject_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        final var playerUuid = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object1 = objectModelFactory.create(player.getId(), UUID.randomUUID().toString(), new byte[5]);
        final var objectName = object1.getName();
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, object1);

        final var object2 = selectObjectOperation.selectObject(TIMEOUT, pgPool, shard, playerUuid, objectName);
        assertEquals(object1, object2);
    }

    @Test
    void givenUnknownUuids_whenSelectObject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var playerId = generateIdOperation.generateId();
        final var objectName = UUID.randomUUID().toString();

        assertThrows(ServerSideNotFoundException.class, () -> selectObjectOperation
                .selectObject(TIMEOUT, pgPool, shard, playerId, objectName));
    }

    long stageId() {
        return generateIdOperation.generateId();
    }
}