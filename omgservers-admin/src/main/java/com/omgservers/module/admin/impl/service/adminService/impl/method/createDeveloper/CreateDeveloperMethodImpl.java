package com.omgservers.module.admin.impl.service.adminService.impl.method.createDeveloper;

import com.omgservers.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.module.tenant.factory.TenantPermissionModelFactory;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.UserModelFactory;
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
        CreateDeveloperAdminRequest.validate(request);
        final var tenantId = request.getTenantId();
        // TODO: improve it
        final var password = String.valueOf(Math.abs(new SecureRandom().nextLong()));
        return getTenant(tenantId)
                .flatMap(tenant -> createUser(password))
                .call(user -> syncCreateProjectPermission(tenantId, user.getId()))
                .map(user -> new CreateDeveloperAdminResponse(user.getId(), password));
    }

    Uni<TenantModel> getTenant(Long tenantId) {
        final var getTenantServiceRequest = new GetTenantShardedRequest(tenantId);
        return tenantModule.getTenantShardedService().getTenant(getTenantServiceRequest)
                .map(GetTenantShardedResponse::getTenant);
    }

    Uni<UserModel> createUser(String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserInternalRequest = new SyncUserShardedRequest(user);
        return userModule.getUserShardedService().syncUser(syncUserInternalRequest)
                .replaceWith(user);
    }

    Uni<TenantPermissionModel> syncCreateProjectPermission(Long tenantId, Long userId) {
        final var tenantPermission = tenantPermissionModelFactory
                .create(tenantId, userId, TenantPermissionEnum.CREATE_PROJECT);
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionShardedRequest(tenantPermission);
        return tenantModule.getTenantShardedService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .replaceWith(tenantPermission);
    }
}