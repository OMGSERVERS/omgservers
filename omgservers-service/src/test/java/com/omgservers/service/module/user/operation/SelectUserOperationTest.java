package com.omgservers.service.module.user.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.selectUser.SelectUserOperation;
import com.omgservers.service.module.user.impl.operation.upsertUser.UpsertUserOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectUserOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

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