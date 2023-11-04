package com.omgservers.module.user.operation;

import com.omgservers.exception.ServerSideUnauthorizedException;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.factory.UserModelFactory;
import com.omgservers.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ValidateCredentialsOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    ValidateCredentialsOperation validateCredentialsOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void giveCorrectPassword_whenValidateCredentials_thenValid() {
        final var userPassword = "password";
        final var passwordHash = BcryptUtil.bcryptHash(userPassword);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var correctPassword = "password";
        validateCredentialsOperation.validateCredentials(TIMEOUT, user, correctPassword);
    }

    @Test
    void giveIncorrectPassword_whenValidateCredentials_thenException() {
        final var userPassword = "password";
        final var passwordHash = BcryptUtil.bcryptHash(userPassword);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var incorrectPassword = "incorrect";
        final var exception = assertThrows(ServerSideUnauthorizedException.class, () -> validateCredentialsOperation
                .validateCredentials(TIMEOUT, user, incorrectPassword));
    }
}