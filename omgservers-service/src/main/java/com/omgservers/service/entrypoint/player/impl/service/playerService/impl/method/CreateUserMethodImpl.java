package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.server.GenerateSecureStringOperation;
import com.omgservers.service.shard.user.UserShard;
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

    final UserShard userShard;

    final GenerateSecureStringOperation generateSecureStringOperation;

    final UserModelFactory userModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateUserPlayerResponse> execute(final CreateUserPlayerRequest request) {
        log.info("Requested, {}", request);

        final var password = generateSecureStringOperation.generateSecureString();
        return createUser(password)
                .invoke(user -> log.info("New user \"{}\" created", user.getId()))
                .map(user -> new CreateUserPlayerResponse(user.getId(), password));
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash, UserConfigDto.create());
        final var request = new SyncUserRequest(user);
        return userShard.getService().execute(request)
                .replaceWith(user);
    }
}
