package com.omgservers.application.module.userModule.impl.operation.validateCredentialsOperation;

import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.exception.ServerSideUnauthorizedException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

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
            throw new ServerSideUnauthorizedException(String.format("credentials are not valid, " +
                    "user=%s", user));
        }
    }
}
