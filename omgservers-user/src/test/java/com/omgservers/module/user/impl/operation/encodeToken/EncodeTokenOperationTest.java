package com.omgservers.module.user.impl.operation.encodeToken;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class EncodeTokenOperationTest extends Assertions {

    @Inject
    EncodeTokenOperation encodeTokenOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenUserToken_whenEncodeToken_thenRawToken() {
        UserTokenModel token = new UserTokenModel();
        token.setId(generateIdOperation.generateId());
        token.setUserId(generateIdOperation.generateId());
        token.setRole(UserRoleEnum.PLAYER);
        token.setSecret(1234567890L);

        String rawToken = encodeTokenOperation.encodeToken(token);
        assertNotNull(rawToken);
    }
}