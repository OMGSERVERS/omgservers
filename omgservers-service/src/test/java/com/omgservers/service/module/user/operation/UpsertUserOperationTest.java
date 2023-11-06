package com.omgservers.service.module.user.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertUserOperationTest extends Assertions {

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void givenNothing_whenUpsertUser_thenInserted() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");

        final var changeContext = upsertUserOperation.upsertUser(shard, user);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.USER_CREATED));
    }

    @Test
    void givenClient_whenUpsertClient_thenUpdated() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);

        final var changeContext = upsertUserOperation.upsertUser(shard, user);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.USER_CREATED));
    }
}