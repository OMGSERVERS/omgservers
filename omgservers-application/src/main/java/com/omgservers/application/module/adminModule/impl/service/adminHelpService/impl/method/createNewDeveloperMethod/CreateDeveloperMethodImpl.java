package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod;

import com.omgservers.base.factory.TenantPermissionModelFactory;
import com.omgservers.base.factory.UserModelFactory;
import com.omgservers.dto.adminModule.CreateDeveloperAdminRequest;
import com.omgservers.dto.adminModule.CreateDeveloperAdminResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionInternalRequest;
import com.omgservers.dto.userModule.SyncUserInternalRequest;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.model.user.UserRoleEnum;
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
        final var userId = generateIdOperation.generateId();
        // TODO: improve it
        final var password = String.valueOf(Math.abs(new SecureRandom().nextLong()));
        final var tenantId = request.getTenantId();

        return getTenant(tenantId)
                .call(ignored -> createUser(userId, password))
                .call(ignored -> syncCreateProjectPermission(tenantId, userId))
                .replaceWith(new CreateDeveloperAdminResponse(userId, password));
    }

    Uni<Void> getTenant(Long tenantId) {
        final var getTenantServiceRequest = new GetTenantInternalRequest(tenantId);
        return tenantModule.getTenantInternalService().getTenant(getTenantServiceRequest)
                .replaceWithVoid();
    }

    Uni<Void> createUser(Long id, String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id, UserRoleEnum.DEVELOPER, passwordHash);
        final var syncUserInternalRequest = new SyncUserInternalRequest(user);
        return userModule.getUserInternalService().syncUser(syncUserInternalRequest)
                .replaceWithVoid();
    }

    Uni<Void> syncCreateProjectPermission(Long tenantId, Long userId) {
        final var entity = tenantPermissionModelFactory.create(tenantId, userId, TenantPermissionEnum.CREATE_PROJECT);
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionInternalRequest(entity);
        return tenantModule.getTenantInternalService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .replaceWithVoid();
    }
}
