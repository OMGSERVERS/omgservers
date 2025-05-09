package com.omgservers.service.shard.user.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.shard.user.operation.testInterface.ValidateCredentialsOperationTestInterface;
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
    void givenUser_whenExecute_thenOk() {
        final var user1 = userModelFactory.create(UserRoleEnum.PLAYER,
                BcryptUtil.bcryptHash("password"),
                UserConfigDto.create());
        final var user2 = validateCredentialsOperation.validateCredentials(user1, "password");
        assertEquals(user1, user2);
    }

    @Test
    void givenUser_whenExecute_thenException() {
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                BcryptUtil.bcryptHash("password"),
                UserConfigDto.create());

        final var exception = assertThrows(ServerSideUnauthorizedException.class, () ->
                validateCredentialsOperation.validateCredentials(user, "wrongpassword"));
        assertEquals(ExceptionQualifierEnum.WRONG_CREDENTIALS, exception.getQualifier());
    }

    @Test
    void givenDeletedUser_whenExecute_thenException() {
        final var user = userModelFactory.create(UserRoleEnum.PLAYER,
                "passwordhash",
                UserConfigDto.create());
        user.setDeleted(true);

        final var exception = assertThrows(ServerSideUnauthorizedException.class, () ->
                validateCredentialsOperation.validateCredentials(user, "password"));
        assertEquals(ExceptionQualifierEnum.USER_NOT_FOUND, exception.getQualifier());
    }
}