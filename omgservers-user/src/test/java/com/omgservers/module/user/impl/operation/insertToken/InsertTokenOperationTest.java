package com.omgservers.module.user.impl.operation.insertToken;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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