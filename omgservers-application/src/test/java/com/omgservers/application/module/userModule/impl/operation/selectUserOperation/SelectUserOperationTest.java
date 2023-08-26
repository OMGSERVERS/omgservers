package com.omgservers.application.module.userModule.impl.operation.selectUserOperation;

import com.omgservers.application.factory.UserModelFactory;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.application.module.userModule.impl.operation.upsertUserOperation.UpsertUserOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class SelectUserOperationTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SelectUserOperation selectUserOperation;

    @Inject
    UpsertUserOperation upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    PgPool pgPool;

    @Test
    void givenUser_whenSelectUser_thenSelected() {
        final var shard = 0;
        final var user1 = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        final var uuid = user1.getId();
        upsertUserOperation.upsertUser(TIMEOUT, pgPool, shard, user1);

        final var user2 = selectUserOperation.selectUser(TIMEOUT, pgPool, shard, uuid);
        assertEquals(user1, user2);
    }

    @Test
    void givenUnknownUuid_whenSelectUser_thenServerSideNotFoundException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectUserOperation
                .selectUser(TIMEOUT, pgPool, shard, id));
    }
}