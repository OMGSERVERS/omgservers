package com.omgservers.service.module.user.operation;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.SelectPlayerOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertPlayerOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectPermissionOperationTest extends Assertions {

    @Inject
    SelectPlayerOperationTestInterface selectPlayerOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UpsertPlayerOperationTestInterface upsertPlayerOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenPlayer_whenSelectPlayer_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        final var userId = user.getId();
        upsertUserOperation.upsertUser(shard, user);
        final var player1 = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var playerId = player1.getId();
        upsertPlayerOperation.upsertPlayer(shard, player1);

        final var player2 = selectPlayerOperation.selectPlayer(shard, userId, playerId, false);
        assertEquals(player1, player2);
    }

    @Test
    void givenUnknownIds_whenSelectPlayer_thenException() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var playerId = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectPlayerOperation
                .selectPlayer(shard, userId, playerId, false));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}