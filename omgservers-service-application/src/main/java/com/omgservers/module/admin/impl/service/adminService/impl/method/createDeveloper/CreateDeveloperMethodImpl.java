package com.omgservers.module.admin.impl.service.adminService.impl.method.createDeveloper;

import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.factory.TenantPermissionModelFactory;
import com.omgservers.module.user.UserModule;
import com.omgservers.factory.UserModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateDeveloperMethodImpl implements CreateDeveloperMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final TenantPermissionModelFactory tenantPermissionModelFactory;
    final GenerateIdOperation generateIdOperation;

    final UserModelFactory userModelFactory;

    @Override
    public Uni<CreateDeveloperAdminResponse> createDeveloper(final CreateDeveloperAdminRequest request) {
        final var tenantId = request.getTenantId();
        // TODO: improve it
        final var password = String.valueOf(new SecureRandom().nextLong());
        return getTenant(tenantId)
                .flatMap(tenant -> createUser(password))
                .call(user -> syncCreateProjectPermission(tenantId, user.getId()))
                .map(user -> new CreateDeveloperAdminResponse(user.getId(), password));
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantServiceRequest = new GetTenantRequest(tenantId);
        return tenantModule.getTenantService().getTenant(getTenantServiceRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<UserModel> createUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserShardedRequest = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(syncUserShardedRequest)
                .replaceWith(user);
    }

    Uni<TenantPermissionModel> syncCreateProjectPermission(Long tenantId, Long userId) {
        final var tenantPermission = tenantPermissionModelFactory
                .create(tenantId, userId, TenantPermissionEnum.CREATE_PROJECT);
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionRequest(tenantPermission);
        return tenantModule.getTenantService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .replaceWith(tenantPermission);
    }
}
