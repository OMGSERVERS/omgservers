package com.omgservers.service.server.bootstrap.impl.method;

import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.alias.CreateUserAliasOperation;
import com.omgservers.service.operation.alias.FindUserAliasOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
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
    final CreateUserAliasOperation createUserAliasOperation;
    final FindUserAliasOperation findUserAliasOperation;

    final AliasModelFactory aliasModelFactory;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<BootstrapDefaultUserResponse> execute(final BootstrapDefaultUserRequest request) {
        log.debug("Bootstrapping default user");

        final var userAlias = request.getAlias();
        return findUserAliasOperation.execute(userAlias)
                .replaceWith(Boolean.FALSE)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> {
                    final var password = request.getPassword();
                    final var role = request.getRole();
                    return createUser(password, role)
                            .flatMap(user -> createUserAliasOperation.execute(user.getId(), userAlias))
                            .replaceWith(Boolean.TRUE);
                })
                .map(BootstrapDefaultUserResponse::new);
    }

    Uni<UserModel> createUser(final String password,
                              final UserRoleEnum role) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(role, passwordHash, UserConfigDto.create());

        final var request = new SyncUserRequest(user);
        return userShard.getService().execute(request)
                .replaceWith(user);
    }
}
