package com.omgservers.service.module.user.operation;

import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.ValidateCredentialsOperationTestInterface;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ValidateCredentialsOperationTest extends Assertions {

    @Inject
    ValidateCredentialsOperationTestInterface validateCredentialsOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void givePassword_whenValidateCredentials_thenValid() {
        final var userPassword = "password";
        final var passwordHash = BcryptUtil.bcryptHash(userPassword);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var correctPassword = "password";
        validateCredentialsOperation.validateCredentials(user, correctPassword);
    }

    @Test
    void givePassword_whenValidateCredentials_thenException() {
        final var userPassword = "password";
        final var passwordHash = BcryptUtil.bcryptHash(userPassword);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var incorrectPassword = "incorrect";
        final var exception = assertThrows(ServerSideUnauthorizedException.class, () -> validateCredentialsOperation
                .validateCredentials(user, incorrectPassword));
    }
}