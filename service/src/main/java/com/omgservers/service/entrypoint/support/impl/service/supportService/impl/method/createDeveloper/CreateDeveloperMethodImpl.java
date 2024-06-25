package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDeveloper;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
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

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(final CreateDeveloperSupportRequest request) {
        log.debug("Create developer, request={}", request);

        final var password = generateSecureStringOperation.generateSecureString();
        return createUser(password)
                .map(user -> new CreateDeveloperSupportResponse(user.getId(), password));
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserShardedRequest = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(syncUserShardedRequest)
                .replaceWith(user);
    }
}
