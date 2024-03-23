package com.omgservers.service.module.user.operation;

import com.omgservers.model.token.TokenModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.TokenModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.service.module.user.operation.testInterface.SelectTokenOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertTokenOperationTestInterface;
import com.omgservers.service.module.user.operation.testInterface.UpsertUserOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTokenOperationTest extends Assertions {

    @Inject
    SelectTokenOperationTestInterface selectTokenOperation;

    @Inject
    UpsertTokenOperationTestInterface upsertTokenOperation;

    @Inject
    UpsertUserOperationTestInterface upsertUserOperation;

    @Inject
    CreateUserTokenOperation createUserTokenOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Inject
    TokenModelFactory tokenModelFactory;

    @Test
    void givenUserToken_whenSelectToken_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var tokenContainer = createUserTokenOperation.createUserToken(user);
        final var tokenModel1 = tokenModelFactory.create(tokenContainer);
        upsertTokenOperation.upsertToken(shard, tokenModel1);

        TokenModel tokenModel2 = selectTokenOperation.selectToken(shard, tokenModel1.getId());
        assertEquals(tokenModel2, tokenModel1);
    }

    @Test
    void givenUnknownId_whenSelectToken_thenException() {
        final var shard = 0;

        assertThrows(ServerSideNotFoundException.class, () -> selectTokenOperation.selectToken(shard, tokenId()));
    }

    Long tokenId() {
        return generateIdOperation.generateId();
    }
}