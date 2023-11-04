package com.omgservers.service.module.user.impl.operation.validateCredentials;

import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import com.omgservers.model.user.UserModel;
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
        if (user == null) {
            throw new ServerSideBadRequestException("user is null");
        }
        if (password == null) {
            throw new ServerSideBadRequestException("password is null");
        }

        if (BcryptUtil.matches(password, user.getPasswordHash())) {
            return Uni.createFrom().item(user);
        } else {
            throw new ServerSideUnauthorizedException(String.format("credentials are not valid, " +
                    "user=%s", user));
        }
    }
}
