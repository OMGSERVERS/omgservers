package com.omgservers.service.shard.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.shard.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertUserOperationTest extends BaseTestClass {

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void givenUser_whenExecute_thenInserted() {
        final var slot = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());

        final var changeContext = upsertUserOperation.upsertUser(slot, user);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.USER_CREATED));
    }

    @Test
    void givenUser_whenExecute_thenUpdated() {
        final var slot = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        upsertUserOperation.upsertUser(slot, user);

        final var changeContext = upsertUserOperation.upsertUser(slot, user);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.USER_CREATED));
    }

    @Test
    void givenUser_whenExecute_thenIdempotencyViolation() {
        final var slot = 0;
        final var user1 = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        upsertUserOperation.upsertUser(slot, user1);

        final var user2 = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create(),
                user1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertUserOperation.upsertUser(slot, user2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}