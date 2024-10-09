package com.omgservers.service.service.bootstrap.impl.method.bootstrapSupportUser;

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
class BootstrapSupportUserMethodImpl implements BootstrapSupportUserMethod {

    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<Void> bootstrapSupportUser() {
        log.debug("Bootstrap support user");

        final var userId = getConfigOperation.getServiceConfig().defaults().supportUserId();
        return getUser(userId)
                .invoke(user -> log.info("Support user was already create, skip operation, userId={}", userId))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var idempotencyKey = "bootstrap/support";
                    final var password = getConfigOperation.getServiceConfig().bootstrap().supportUser().password();
                    final var passwordHash = BcryptUtil.bcryptHash(password);
                    final var user = userModelFactory.create(userId,
                            UserRoleEnum.SUPPORT,
                            passwordHash, idempotencyKey);

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
