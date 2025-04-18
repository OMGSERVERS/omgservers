package com.omgservers.service.shard.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.user.operation.testInterface.DeletePlayerOperationTestInterface;
import com.omgservers.service.shard.user.operation.testInterface.UpsertPlayerOperationTestInterface;
import com.omgservers.service.shard.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeletePlayerOperationTest extends BaseTestClass {

    @Inject
    DeletePlayerOperationTestInterface deletePlayerOperation;

    @Inject
    UpsertPlayerOperationTestInterface upsertPlayerOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Test
    void givenUserPlayer_whenExecute_thenDeleted() {
        final var slot = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        upsertUserOperation.upsertUser(slot, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var id = player.getId();
        upsertPlayerOperation.upsertPlayer(slot, player);

        final var changeContext = deletePlayerOperation.deletePlayer(slot, user.getId(), id);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.PLAYER_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenSkip() {
        final var slot = 0;
        final var userId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deletePlayerOperation.deletePlayer(slot, userId, id);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.PLAYER_DELETED));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    long stageId() {
        return generateIdOperation.generateId();
    }
}