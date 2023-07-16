package com.omgservers.application.module.userModule.impl.operation.selectPlayerOperation;

import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectPermissionOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectPlayerOperation selectPlayerOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenPlayer_whenSelectPlayer_thenSelected() {
        final var shard = 0;
        final var user = UserModel.create(UserRoleEnum.PLAYER, "passwordhash");
        final var userUuid = user.getUuid();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);
        final var player1 = PlayerModel.create(user.getUuid(), UUID.randomUUID(), PlayerConfigModel.create());
        final var stageUuid = player1.getStage();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player1);

        final var player2 = selectPlayerOperation.selectPlayer(TIMEOUT, pgPool, shard, userUuid, stageUuid);
        assertEquals(player1, player2);
    }

    @Test
    void givenUnknownUuids_whenSelectPlayer_thenServerSideNotFoundException() {
        final var shard = 0;
        final var userUuid = UUID.randomUUID();
        final var stageUuid = UUID.randomUUID();

        assertThrows(ServerSideNotFoundException.class, () -> selectPlayerOperation
                .selectPlayer(TIMEOUT, pgPool, shard, userUuid, stageUuid));
    }
}