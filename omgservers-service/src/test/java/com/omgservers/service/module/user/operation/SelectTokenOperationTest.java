package com.omgservers.service.module.user.operation;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.model.token.TokenModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.TokenModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import com.omgservers.service.module.user.impl.operation.selectToken.SelectTokenOperation;
import com.omgservers.service.module.user.impl.operation.upsertToken.UpsertTokenOperation;
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
class SelectTokenOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectTokenOperation selectTokenOperation;

    @Inject
    UpsertTokenOperation upsertTokenOperation;

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

    @Inject
    PgPool pgPool;

    @Test
    void givenUserToken_whenSelectToken_thenSelected() {
        final var shard = 0;
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        upsertUserOperation.upsertUser(shard, user);
        final var tokenContainer = createUserTokenOperation.createUserToken(user);
        final var tokenModel1 = tokenModelFactory.create(tokenContainer);
        assertTrue(upsertTokenOperation.upsertToken(TIMEOUT, pgPool, shard, tokenModel1));

        TokenModel tokenModel2 = selectTokenOperation.selectToken(TIMEOUT, pgPool, shard, tokenModel1.getId());
        assertEquals(tokenModel2, tokenModel1);
    }

    @Test
    void givenUnknownId_whenSelectToken_thenException() {
        final var shard = 0;

        assertThrows(ServerSideNotFoundException.class, () -> selectTokenOperation
                .selectToken(TIMEOUT, pgPool, shard, tokenId()));
    }

    Long tokenId() {
        return generateIdOperation.generateId();
    }
}