package com.omgservers.module.user.impl.operation.selectObject;

import com.omgservers.exception.ServerSideNotFoundException;
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
class SelectObjectOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

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
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId(), PlayerConfigModel.create());
        final var playerId = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);
        final var object1 = objectModelFactory.create(user.getId(), playerId, UUID.randomUUID().toString(), new byte[5]);
        final var objectName = object1.getName();
        upsertObjectOperation.upsertObject(TIMEOUT, pgPool, shard, user.getId(), object1);

        final var object2 = selectObjectOperation.selectObject(TIMEOUT, pgPool, shard, user.getId(), playerId, objectName);
        assertEquals(object1, object2);
    }

    @Test
    void givenUnknownUuids_whenSelectObject_thenServerSideNotFoundException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var playerId = generateIdOperation.generateId();
        final var objectName = UUID.randomUUID().toString();

        assertThrows(ServerSideNotFoundException.class, () -> selectObjectOperation
                .selectObject(TIMEOUT, pgPool, shard, userId, playerId, objectName));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    long stageId() {
        return generateIdOperation.generateId();
    }
}