package com.omgservers.service.module.user.operation;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.TokenModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.service.module.user.operation.testInterface.UpsertTokenOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTokenOperationTest extends Assertions {

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UpsertTokenOperationTestInterface upsertTokenOperation;

    @Inject
    CreateUserTokenOperation createUserTokenOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    TokenModelFactory tokenModelFactory;

    @Test
    void givenToken_whenUpsertToken_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);

        final var tokenContainer = createUserTokenOperation.createUserToken(user);
        final var tokenModel = tokenModelFactory.create(tokenContainer);

        final var changeContext = upsertTokenOperation.upsertToken(shard, tokenModel);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenToken_whenUpsertToken_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);

        final var tokenContainer = createUserTokenOperation.createUserToken(user);
        final var tokenModel = tokenModelFactory.create(tokenContainer);
        upsertTokenOperation.upsertToken(shard, tokenModel);

        final var changeContext = upsertTokenOperation.upsertToken(shard, tokenModel);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenToken_whenUpsertToken_thenIdempotencyViolation() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);

        final var tokenContainer1 = createUserTokenOperation.createUserToken(user);
        final var tokenModel1 = tokenModelFactory.create(tokenContainer1);
        upsertTokenOperation.upsertToken(shard, tokenModel1);

        final var tokenContainer2 = createUserTokenOperation.createUserToken(user);
        final var tokenModel2 = tokenModelFactory.create(tokenContainer2, tokenModel1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTokenOperation.upsertToken(shard, tokenModel2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
    }
}