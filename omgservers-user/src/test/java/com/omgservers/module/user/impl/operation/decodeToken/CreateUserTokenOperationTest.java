package com.omgservers.module.user.impl.operation.decodeToken;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.module.user.impl.operation.encodeToken.EncodeTokenOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class CreateUserTokenOperationTest extends Assertions {

    @Inject
    DecodeTokenOperation decodeTokenOperation;

    @Inject
    EncodeTokenOperation encodeTokenOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenRawToken_whenDecodeToken_thenToken() {
        UserTokenModel userToken1 = new UserTokenModel();
        userToken1.setId(generateIdOperation.generateId());
        userToken1.setUserId(generateIdOperation.generateId());
        userToken1.setRole(UserRoleEnum.PLAYER);
        userToken1.setSecret(1234567890L);

        String rawToken = encodeTokenOperation.encodeToken(userToken1);
        log.info("Raw token, {}", rawToken);

        UserTokenModel userToken2 = decodeTokenOperation.decodeToken(rawToken);
        assertEquals(userToken1, userToken2);
        log.info("Token, {}", userToken2);
    }

    @Test
    void givenInvalidToken_whenDecodeToken_thenException() {
        String rawToken = "invalidtoken";
        final var exception = assertThrows(ServerSideBadRequestException.class, () -> decodeTokenOperation.decodeToken(rawToken));
        log.info("Exception: {}", exception.getMessage());
    }
}