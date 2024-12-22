package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDefaultUserMethodImpl implements BootstrapDefaultUserMethod {

    final AliasModule aliasModule;

    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final AliasModelFactory aliasModelFactory;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<BootstrapDefaultUserResponse> execute(final BootstrapDefaultUserRequest request) {
        log.debug("Bootstrap of default user");

        final var userAlias = request.getAlias();
        return findUserAlias(userAlias)
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var password = request.getPassword();
                    final var role = request.getRole();
                    return createUser(password, role)
                            .flatMap(user -> createUserAlias(user.getId(), userAlias)
                                    .invoke(alias -> log.info("Default user {} under alias {} was created",
                                            user.getId(), userAlias)))
                            .replaceWith(Boolean.TRUE);
                })
                .map(BootstrapDefaultUserResponse::new);
    }

    Uni<AliasModel> findUserAlias(final String alias) {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY, alias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<AliasModel> createUserAlias(final Long userId, final String userAlias) {
        final var alias = aliasModelFactory.create(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                userAlias, userId);
        final var request = new SyncAliasRequest(alias);
        return aliasModule.getService().execute(request)
                .replaceWith(alias);
    }

    Uni<UserModel> createUser(final String password,
                              final UserRoleEnum role) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(role, passwordHash);

        final var request = new SyncUserRequest(user);
        return userModule.getService().syncUser(request)
                .replaceWith(user);
    }
}
