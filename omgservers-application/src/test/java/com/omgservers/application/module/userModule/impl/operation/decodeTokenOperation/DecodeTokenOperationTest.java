package com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation;

import com.omgservers.application.module.userModule.model.user.UserTokenModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
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
        String rawToken = "eyJ1dWlkIjoiMjQ3MDI0ZDctZjU1NS00ZjRkLTkxZjItOWFkMDkzOTQzMzk4IiwidGVuYW50IjoiMzc1ZDFlYmEtNGNkMS00MTUzLThhOTYtNGVkNzg0ZGEzYzJlIiwidXNlciI6ImE1OWZmYjM4LWIxZDctNGNhNy1iNzI2LTgxMzRkMThkYWQ0MiIsInJvbGUiOiJQTEFZRVIiLCJzZWNyZXQiOjEyMzQ1Njc4OTB9";
        UserTokenModel token = decodeTokenOperation.decodeToken(rawToken);
        UserTokenModel.validateUserTokenModel(token);
        log.info("Token, {}", token);
    }

    @Test
    void givenInvalidToken_whenDecodeToken_thenToken() {
        String rawToken = "invalidtoken";
        assertThrows(ServerSideBadRequestException.class, () -> decodeTokenOperation.decodeToken(rawToken));
    }
}