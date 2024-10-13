package com.omgservers.service.module.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.operation.testInterface.ValidateCredentialsOperationTestInterface;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ValidateCredentialsOperationTest extends BaseTestClass {

    @Inject
    ValidateCredentialsOperationTestInterface validateCredentialsOperation;

    @Inject
    UserModelFactory userModelFactory;

    @Test
    void givenUser_whenValidateCredentials_thenOk() {
        final var user1 = userModelFactory.create(UserRoleEnum.PLAYER, BcryptUtil.bcryptHash("password"));
        final var user2 = validateCredentialsOperation.validateCredentials(user1, "password");
        assertEquals(user1, user2);
    }

    @Test
    void givenUser_whenValidateCredentials_thenException() {
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, BcryptUtil.bcryptHash("password"));

        final var exception = assertThrows(ServerSideUnauthorizedException.class, () ->
                validateCredentialsOperation.validateCredentials(user, "wrongpassword"));
        assertEquals(ExceptionQualifierEnum.WRONG_CREDENTIALS, exception.getQualifier());
    }

    @Test
    void givenDeletedUser_whenValidateCredentials_thenException() {
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, "passwordhash");
        user.setDeleted(true);

        final var exception = assertThrows(ServerSideUnauthorizedException.class, () ->
                validateCredentialsOperation.validateCredentials(user, "password"));
        assertEquals(ExceptionQualifierEnum.USER_NOT_FOUND, exception.getQualifier());
    }
}