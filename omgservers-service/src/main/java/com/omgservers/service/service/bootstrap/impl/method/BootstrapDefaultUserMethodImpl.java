package com.omgservers.service.service.bootstrap.impl.method;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.service.bootstrap.dto.BootstrapDefaultUserResponse;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class BootstrapDefaultUserMethodImpl implements BootstrapDefaultUserMethod {

    final AliasShard aliasShard;

    final UserShard userShard;

    final GetServiceConfigOperation getServiceConfigOperation;

    final AliasModelFactory aliasModelFactory;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<BootstrapDefaultUserResponse> execute(final BootstrapDefaultUserRequest request) {
        log.debug("Bootstrapping default user");

        final var userAlias = request.getAlias();
        return findDefaultUserAlias(userAlias)
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var password = request.getPassword();
                    final var role = request.getRole();
                    return createUser(password, role)
                            .flatMap(user -> createDefaultUserAlias(user.getId(), userAlias)
                                    .invoke(alias -> log.info(
                                            "The default user \"{}\" under the alias \"{}\" was created",
                                            user.getId(), userAlias)))
                            .replaceWith(Boolean.TRUE);
                })
                .map(BootstrapDefaultUserResponse::new);
    }

    Uni<AliasModel> findDefaultUserAlias(final String alias) {
        final var request = new FindAliasRequest(GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.DEFAULT_USER_GROUP,
                alias);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<AliasModel> createDefaultUserAlias(final Long userId, final String userAlias) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.USER,
                GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.DEFAULT_USER_GROUP,
                userId,
                userAlias);
        final var request = new SyncAliasRequest(alias);
        return aliasShard.getService().execute(request)
                .replaceWith(alias);
    }

    Uni<UserModel> createUser(final String password,
                              final UserRoleEnum role) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(role, passwordHash);

        final var request = new SyncUserRequest(user);
        return userShard.getService().execute(request)
                .replaceWith(user);
    }
}
