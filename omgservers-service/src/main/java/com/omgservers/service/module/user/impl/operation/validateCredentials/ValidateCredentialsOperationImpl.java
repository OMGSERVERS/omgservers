package com.omgservers.service.module.user.impl.operation.validateCredentials;

import com.omgservers.model.user.UserModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: unit test it

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateCredentialsOperationImpl implements ValidateCredentialsOperation {

    @Override
    public Uni<UserModel> validateCredentials(final UserModel user,
                                              final String password) {
        if (BcryptUtil.matches(password, user.getPasswordHash())) {
            return Uni.createFrom().item(user);
        } else {
            throw new ServerSideUnauthorizedException(
                    ExceptionQualifierEnum.CREDENTIALS_WRONG,
                    String.format("wrong credentials, user=%s", user));
        }
    }
}
