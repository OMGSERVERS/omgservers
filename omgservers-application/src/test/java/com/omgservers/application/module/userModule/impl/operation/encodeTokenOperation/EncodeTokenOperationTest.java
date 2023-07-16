package com.omgservers.application.module.userModule.impl.operation.encodeTokenOperation;

import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.module.userModule.model.user.UserTokenModel;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import java.util.UUID;

@Slf4j
@QuarkusTest
class EncodeTokenOperationTest extends Assertions {

    @Inject
    EncodeTokenOperation encodeTokenOperation;

    @Test
    void givenToken_whenEncodeToken_thenRawToken() {
        UserTokenModel token = new UserTokenModel();
        token.setUuid(UUID.randomUUID());
        token.setUser(UUID.randomUUID());
        token.setRole(UserRoleEnum.PLAYER);
        token.setSecret(1234567890L);

        String rawToken = encodeTokenOperation.encodeToken(token);
        assertNotNull(rawToken);
        log.info("Raw token, {}", rawToken);
    }
}