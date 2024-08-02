package com.omgservers.service.server.service.bootstrap.impl.method.bootstrapServiceUser;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapServiceUserMethodImpl implements BootstrapServiceUserMethod {

    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<Void> bootstrapServiceUser() {
        log.debug("Bootstrap service user");

        final var userId = getConfigOperation.getServiceConfig().defaults().serviceUserId();
        return getUser(userId)
                .invoke(user -> log.info("Service user was already create, skip operation, userId={}", userId))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var idempotencyKey = "bootstrap/serviceUser";
                    final var password = getConfigOperation.getServiceConfig().bootstrap().serviceUser().password();
                    final var passwordHash = BcryptUtil.bcryptHash(password);
                    final var user = userModelFactory.create(userId,
                            UserRoleEnum.SERVICE,
                            passwordHash,
                            idempotencyKey);

                    return syncUser(user)
                            .replaceWith(user);
                })
                .replaceWithVoid();
    }

    Uni<UserModel> getUser(final Long userId) {
        final var request = new GetUserRequest(userId);
        return userModule.getUserService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<Boolean> syncUser(final UserModel userModel) {
        final var request = new SyncUserRequest(userModel);
        return userModule.getUserService().syncUserWithIdempotency(request)
                .map(SyncUserResponse::getCreated);
    }
}
