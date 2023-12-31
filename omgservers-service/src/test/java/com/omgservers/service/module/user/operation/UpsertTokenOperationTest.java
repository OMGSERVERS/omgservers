package com.omgservers.service.module.user.operation;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.TokenModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.service.module.user.impl.operation.upsertToken.UpsertTokenOperation;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTokenOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertTokenOperation upsertTokenOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    CreateUserTokenOperation createUserTokenOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    TokenModelFactory tokenModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenToken_whenUpsertToken_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);

        final var tokenContainer = createUserTokenOperation.createUserToken(user);
        final var tokenModel = tokenModelFactory.create(tokenContainer);

        assertTrue(upsertTokenOperation.upsertToken(TIMEOUT, pgPool, shard, tokenModel));
    }
}