package com.omgservers.service.module.user.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.upsertPlayer.UpsertPlayerOperation;
import com.omgservers.service.module.user.operation.testInterface.DeletePlayerOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteUserOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    DeletePlayerOperationTestInterface deletePlayerOperation;

    @Inject
    UpsertPlayerOperation upsertPlayerOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PlayerModelFactory playerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenUserPlayer_whenDeletePlayer_thenDeleted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var id = player.getId();
        upsertPlayerOperation.upsertPlayer(TIMEOUT, pgPool, shard, player);

        final var changeContext = deletePlayerOperation.deletePlayer(shard, user.getId(), id);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.PLAYER_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeletePlayer_thenFalse() {
        final var shard = 0;
        final var userId = generateIdOperation.generateId();
        final var id = generateIdOperation.generateId();

        final var changeContext = deletePlayerOperation.deletePlayer(shard, userId, id);
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