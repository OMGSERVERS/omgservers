package com.omgservers.service.module.user.operation;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.SelectUserOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
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
    SelectUserOperationTestInterface selectUserOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

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
        final var id = user1.getId();
        upsertUserOperation.upsertUser(shard, user1);

        final var user2 = selectUserOperation.selectUser(shard, id);
        assertEquals(user1, user2);
    }

    @Test
    void givenUnknownId_whenSelectUser_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectUserOperation
                .selectUser(shard, id));
    }
}