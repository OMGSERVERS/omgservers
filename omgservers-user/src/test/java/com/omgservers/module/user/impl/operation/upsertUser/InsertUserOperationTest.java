package com.omgservers.module.user.impl.operation.upsertUser;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.user.factory.UserModelFactory;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class InsertUserOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void givenNothing_whenUpsertUser_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");

        assertTrue(upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user));
    }

    @Test
    void givenClient_whenUpsertClient_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user);

        assertFalse(upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user));
    }
}