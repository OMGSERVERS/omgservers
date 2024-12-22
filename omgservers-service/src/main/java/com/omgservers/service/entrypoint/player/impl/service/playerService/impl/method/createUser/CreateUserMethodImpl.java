package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createUser;

import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateUserMethodImpl implements CreateUserMethod {

    final UserModule userModule;

    final GenerateSecureStringOperation generateSecureStringOperation;

    final UserModelFactory userModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateUserPlayerResponse> createUser(final CreateUserPlayerRequest request) {
        log.debug("Requested, {}", request);

        final var password = generateSecureStringOperation.generateSecureString();
        return createUser(password)
                .invoke(user -> log.info("The new user \"{}\" was created", user.getId()))
                .map(user -> new CreateUserPlayerResponse(user.getId(), password));
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash);
        final var request = new SyncUserRequest(user);
        return userModule.getService().syncUser(request)
                .replaceWith(user);
    }
}
