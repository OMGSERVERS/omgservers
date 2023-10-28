package com.omgservers.module.user.operation;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.user.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.createUserToken.CreateUserTokenOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class CreateUserTokenOperationTest extends Assertions {

    @Inject
    CreateUserTokenOperation createUserTokenOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void givenUser_whenCreateUserToken_thenCreated() {
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, "passwordhash");
        final var tokenContainer = createUserTokenOperation.createUserToken(user);

        assertNotNull(tokenContainer);
        assertEquals(user.getId(), tokenContainer.getTokenObject().getUserId());
        assertEquals(user.getRole(), tokenContainer.getTokenObject().getRole());
        assertTrue(tokenContainer.getLifetime() > 0);
    }
}