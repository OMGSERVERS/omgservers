package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createSupport;

import com.omgservers.model.dto.admin.CreateSupportAdminRequest;
import com.omgservers.model.dto.admin.CreateSupportAdminResponse;
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
class CreateSupportMethodImpl implements CreateSupportMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GenerateIdOperation generateIdOperation;

    final TenantPermissionModelFactory tenantPermissionModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public Uni<CreateSupportAdminResponse> createSupport(final CreateSupportAdminRequest request) {
        log.debug("Create support, request={}", request);

        final var password = generateSecureStringOperation.generateSecureString();
        return createUser(password)
                .map(user -> new CreateSupportAdminResponse(user.getId(), password));
    }

    Uni<UserModel> createUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.SUPPORT, passwordHash);
        final var syncUserShardedRequest = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(syncUserShardedRequest)
                .replaceWith(user);
    }
}
