package com.omgservers.module.user.impl.operation.selectPlayer;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.user.UserRoleEnum;
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

@Slf4j
@QuarkusTest
class SelectPermissionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectPlayerOperation selectPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenPlayer_whenSelectPlayer_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        final var userUuid = user.getId();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player1 = playerModelFactory.create(user.getId(), stageId(), PlayerConfigModel.create());
        final var stageUuid = player1.getStageId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player1);

        final var player2 = selectPlayerOperation.selectPlayer(TIMEOUT, pgPool, shard, userUuid, stageUuid);
        assertEquals(player1, player2);
    }

    @Test
    void givenUnknownUuids_whenSelectPlayer_thenServerSideNotFoundException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var stageId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectPlayerOperation
                .selectPlayer(TIMEOUT, pgPool, shard, userId, stageId));
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}