package com.omgservers.service.service.bootstrap.impl.method.bootstrapRegistryUser;

import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapRegistryUserMethodImpl implements BootstrapRegistryUserMethod {

    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<Void> bootstrapRegistryUser() {
        log.debug("Bootstrap registry user");

        final var userId = getConfigOperation.getServiceConfig().defaults().registryUserId();
        return getUser(userId)
                .invoke(user -> log.info("Registry user was already create, skip operation, userId={}", userId))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var idempotencyKey = "bootstrap/registryUser";
                    final var password = getConfigOperation.getServiceConfig().bootstrap().registryUser().password();
                    final var passwordHash = BcryptUtil.bcryptHash(password);
                    final var user = userModelFactory.create(userId,
                            UserRoleEnum.REGISTRY,
                            passwordHash,
                            idempotencyKey);

                    return syncUser(user)
                            .replaceWith(user);
                })
                .replaceWithVoid();
    }

    Uni<UserModel> getUser(final Long userId) {
        final var request = new GetUserRequest(userId);
        return userModule.getService().getUser(request)
                .map(GetUserResponse::getUser);
    }

    Uni<Boolean> syncUser(final UserModel userModel) {
        final var request = new SyncUserRequest(userModel);
        return userModule.getService().syncUserWithIdempotency(request)
                .map(SyncUserResponse::getCreated);
    }
}
