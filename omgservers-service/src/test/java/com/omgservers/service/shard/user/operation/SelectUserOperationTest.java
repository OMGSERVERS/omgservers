package com.omgservers.service.shard.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.shard.user.operation.testInterface.SelectUserOperationTestInterface;
import com.omgservers.service.shard.user.operation.testInterface.UpsertUserOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectUserOperationTest extends BaseTestClass {

    @Inject
    SelectUserOperationTestInterface selectUserOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenUser_whenExecute_thenSelected() {
        final var shard = 0;
        final var user1 = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        final var id = user1.getId();
        upsertUserOperation.upsertUser(shard, user1);

        final var user2 = selectUserOperation.selectUser(shard, id);
        assertEquals(user1, user2);
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectUserOperation
                .selectUser(shard, id));
    }
}