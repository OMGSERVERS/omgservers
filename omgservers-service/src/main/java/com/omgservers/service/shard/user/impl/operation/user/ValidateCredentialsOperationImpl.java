package com.omgservers.service.shard.user.impl.operation.user;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.exception.ServerSideUnauthorizedException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ValidateCredentialsOperationImpl implements ValidateCredentialsOperation {

    @Override
    public Uni<UserModel> execute(final UserModel user,
                                  final String password) {
        if (user.getDeleted()) {
            throw new ServerSideUnauthorizedException(
                    ExceptionQualifierEnum.USER_NOT_FOUND,
                    String.format("user was already deleted, userId=%s", user.getId()));
        }

        if (BcryptUtil.matches(password, user.getPasswordHash())) {
            return Uni.createFrom().item(user);
        } else {
            throw new ServerSideUnauthorizedException(
                    ExceptionQualifierEnum.WRONG_CREDENTIALS,
                    String.format("wrong credentials, user=%s", user));
        }
    }
}
