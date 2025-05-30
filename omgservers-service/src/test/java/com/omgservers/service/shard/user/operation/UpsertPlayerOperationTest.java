package com.omgservers.service.shard.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.user.operation.testInterface.UpsertPlayerOperationTestInterface;
import com.omgservers.service.shard.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertPlayerOperationTest extends BaseTestClass {

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
    void givenPlayer_whenExecute_thenInserted() {
        final var slot = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        upsertUserOperation.upsertUser(slot, user);

        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        final var changeContext = upsertPlayerOperation.upsertPlayer(slot, player);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenPlayer_whenExecute_thenUpdated() {
        final var slot = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        upsertUserOperation.upsertUser(slot, user);

        final var player = playerModelFactory.create(user.getId(), tenantId(), stageId());
        upsertPlayerOperation.upsertPlayer(slot, player);

        final var changeContext = upsertPlayerOperation.upsertPlayer(slot, player);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenPlayer_whenExecute_thenIdempotencyViolation() {
        final var slot = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        upsertUserOperation.upsertUser(slot, user);

        final var player1 = playerModelFactory.create(user.getId(), tenantId(), stageId());
        upsertPlayerOperation.upsertPlayer(slot, player1);

        final var player2 = playerModelFactory.create(user.getId(), tenantId(), stageId(), player1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertPlayerOperation.upsertPlayer(slot, player2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}