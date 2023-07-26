package com.omgservers.application.module.userModule.impl.operation.upsertUserOperation;

import com.omgservers.application.module.userModule.model.user.UserModelFactory;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class InsertUserOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

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