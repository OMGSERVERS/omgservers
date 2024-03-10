package com.omgservers.service.module.user.impl.operation.createUserToken;

import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.service.module.user.impl.operation.encodeToken.EncodeTokenOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateUserTokenOperationImpl implements CreateUserTokenOperation {

    final EncodeTokenOperation encodeTokenOperation;
    final GenerateIdOperation generateIdOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public UserTokenContainerModel createUserToken(final UserModel user) {
        final var lifetime = getConfigOperation.getServiceConfig().clientTokenLifetime();
        final var userId = user.getId();

        var tokenObject = new UserTokenModel();
        tokenObject.setId(generateIdOperation.generateId());
        tokenObject.setUserId(userId);
        tokenObject.setRole(user.getRole());
        // TODO improve secret generation
        tokenObject.setSecret(new SecureRandom().nextLong());

        final var rawToken = encodeTokenOperation.encodeToken(tokenObject);
        final var result = new UserTokenContainerModel(tokenObject, rawToken, lifetime);
        return result;
    }
}
