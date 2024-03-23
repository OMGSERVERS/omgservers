package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method.createDeveloper;

import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.TenantPermissionModelFactory;
import com.omgservers.service.factory.UserModelFactory;
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
    public Uni<CreateDeveloperAdminResponse> createDeveloper(final CreateDeveloperAdminRequest request) {
        log.debug("Create developer, request={}", request);

        final var tenantId = request.getTenantId();
        final var password = generateSecureStringOperation.generateSecureString();
        return getTenant(tenantId)
                .flatMap(tenant -> createUser(password))
                .call(user -> syncCreateProjectPermission(tenantId, user.getId()))
                .call(user -> syncGetDashboardPermission(tenantId, user.getId()))
                .map(user -> new CreateDeveloperAdminResponse(user.getId(), password));
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantModule.getTenantService().getTenant(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<UserModel> createUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserShardedRequest = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(syncUserShardedRequest)
                .replaceWith(user);
    }

    Uni<Boolean> syncCreateProjectPermission(Long tenantId, Long userId) {
        final var tenantPermission = tenantPermissionModelFactory
                .create(tenantId, userId, TenantPermissionEnum.CREATE_PROJECT);
        return syncTenantPermission(tenantPermission);
    }

    Uni<Boolean> syncGetDashboardPermission(Long tenantId, Long userId) {
        final var tenantPermission = tenantPermissionModelFactory
                .create(tenantId, userId, TenantPermissionEnum.GET_DASHBOARD);
        return syncTenantPermission(tenantPermission);
    }

    Uni<Boolean> syncTenantPermission(TenantPermissionModel tenantPermission) {
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionRequest(tenantPermission);
        return tenantModule.getTenantService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .map(SyncTenantPermissionResponse::getCreated);
    }
}
