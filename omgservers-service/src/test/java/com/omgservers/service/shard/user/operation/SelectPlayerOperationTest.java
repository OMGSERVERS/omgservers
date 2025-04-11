package com.omgservers.service.shard.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.user.operation.testInterface.SelectPlayerOperationTestInterface;
import com.omgservers.service.shard.user.operation.testInterface.UpsertPlayerOperationTestInterface;
import com.omgservers.service.shard.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectPlayerOperationTest extends BaseTestClass {

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
    void givenPlayer_whenExecute_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash", UserConfigDto.create());
        final var userId = user.getId();
        upsertUserOperation.upsertUser(shard, user);
        final var player1 = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var playerId = player1.getId();
        upsertPlayerOperation.upsertPlayer(shard, player1);

        final var player2 = selectPlayerOperation.selectPlayer(shard, userId, playerId, false);
        assertEquals(player1, player2);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
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