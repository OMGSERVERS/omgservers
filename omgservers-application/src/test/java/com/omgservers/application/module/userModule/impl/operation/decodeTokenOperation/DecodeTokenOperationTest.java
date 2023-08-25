package com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation;

import com.omgservers.model.user.UserTokenModel;
import com.omgservers.exception.ServerSideBadRequestException;
import io.quarkus.test.junit.QuarkusTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

@Slf4j
@QuarkusTest
class DecodeTokenOperationTest extends Assertions {

    @Inject
    DecodeTokenOperation decodeTokenOperation;

    @Test
    void givenToken_whenDecodeToken_thenToken() {
        String rawToken = "eyJpZCI6Mjg5NzQyMzQ0Mzc1ODA4MCwidXNlciI6Mjg5NzQyMzQ0Mzc1ODA4MSwicm9sZSI6IlBMQVlFUiIsInNlY3JldCI6MTIzNDU2Nzg5MH0=";
        UserTokenModel token = decodeTokenOperation.decodeToken(rawToken);
        UserTokenModel.validate(token);
        log.info("Token, {}", token);
    }

    @Test
    void givenInvalidToken_whenDecodeToken_thenToken() {
        String rawToken = "invalidtoken";
        assertThrows(ServerSideBadRequestException.class, () -> decodeTokenOperation.decodeToken(rawToken));
    }
}