package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createUser;

import com.omgservers.model.dto.player.CreateUserPlayerRequest;
import com.omgservers.model.dto.player.CreateUserPlayerResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.user.UserModule;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateUserMethodImpl implements CreateUserMethod {

    final UserModule userModule;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<CreateUserPlayerResponse> createUser(final CreateUserPlayerRequest request) {
        log.debug("Create user, request={}", request);

        //TODO: improve it
        final var password = String.valueOf(new SecureRandom().nextLong());

        return createUser(password)
                .map(user -> new CreateUserPlayerResponse(user.getId(), password));
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.PLAYER, passwordHash);
        final var request = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(request)
                .replaceWith(user);
    }
}
