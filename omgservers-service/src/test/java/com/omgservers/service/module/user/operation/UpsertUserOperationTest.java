package com.omgservers.service.module.user.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertUserOperationTest extends Assertions {

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void givenUser_whenUpsertUser_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");

        final var changeContext = upsertUserOperation.upsertUser(shard, user);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.USER_CREATED));
    }

    @Test
    void givenUser_whenUpsertUser_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);

        final var changeContext = upsertUserOperation.upsertUser(shard, user);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.USER_CREATED));
    }

    @Test
    void givenUser_whenUpsertUser_thenIdempotencyViolation() {
        final var shard = 0;
        final var user1 = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user1);

        final var user2 = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                user1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertUserOperation.upsertUser(shard, user2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}