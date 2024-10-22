package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
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

    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GenerateIdOperation generateIdOperation;

    final TenantPermissionModelFactory tenantPermissionModelFactory;
    final UserModelFactory userModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateDeveloperSupportResponse> execute(final CreateDeveloperSupportRequest request) {
        log.info("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var password = generateSecureStringOperation.generateSecureString();
        return createUser(password)
                .map(user -> new CreateDeveloperSupportResponse(user.getId(), password));
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserShardedRequest = new SyncUserRequest(user);
        return userModule.getService().syncUser(syncUserShardedRequest)
                .replaceWith(user);
    }
}
