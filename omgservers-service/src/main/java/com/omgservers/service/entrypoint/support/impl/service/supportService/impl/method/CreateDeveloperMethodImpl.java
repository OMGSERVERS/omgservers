package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.operation.server.GenerateSecureStringOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateDeveloperMethodImpl implements CreateDeveloperMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GenerateIdOperation generateIdOperation;

    final TenantPermissionModelFactory tenantPermissionModelFactory;
    final UserModelFactory userModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateDeveloperSupportResponse> execute(final CreateDeveloperSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var password = generateSecureStringOperation.generateSecureString();
        return createDeveloperUser(password)
                .invoke(developerUser -> {
                    log.info("New developer user \"{}\" created by {}", developerUser.getId(), userId);
                })
                .map(user -> new CreateDeveloperSupportResponse(user.getId(), password));
    }

    Uni<UserModel> createDeveloperUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash, UserConfigDto.create());
        final var syncUserShardedRequest = new SyncUserRequest(user);
        return userShard.getService().execute(syncUserShardedRequest)
                .replaceWith(user);
    }
}
