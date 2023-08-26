package com.omgservers.application.module.userModule.impl.operation.insertTokenOperation;

import com.omgservers.application.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class InsertTokenOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    InsertTokenOperation insertTokenOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenUser_whenInsertToken_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);

        final var tokenContainer = insertTokenOperation.insertToken(TIMEOUT, pgPool, shard, user);
        assertNotNull(tokenContainer);
        UserTokenContainerModel.validateUserTokenContainerModel(tokenContainer);
        assertEquals(user.getId(), tokenContainer.getTokenObject().getUserId());
        assertEquals(user.getRole(), tokenContainer.getTokenObject().getRole());
        assertTrue(tokenContainer.getLifetime() > 0);
    }
}