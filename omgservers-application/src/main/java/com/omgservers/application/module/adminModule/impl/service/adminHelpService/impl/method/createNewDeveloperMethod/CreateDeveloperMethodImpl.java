package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionModelFactory;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserModelFactory;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.GetTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantPermissionInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.CreateUserInternalRequest;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
    public Uni<CreateDeveloperHelpResponse> createDeveloper(final CreateDeveloperHelpRequest request) {
        CreateDeveloperHelpRequest.validate(request);
        final var userId = generateIdOperation.generateId();
        // TODO: improve it
        final var password = String.valueOf(Math.abs(new SecureRandom().nextLong()));
        final var tenantId = request.getTenantId();

        return getTenant(tenantId)
                .call(ignored -> createUser(userId, password))
                .call(ignored -> syncCreateProjectPermission(tenantId, userId))
                .replaceWith(new CreateDeveloperHelpResponse(userId, password));
    }

    Uni<Void> getTenant(Long tenantId) {
        final var getTenantServiceRequest = new GetTenantInternalRequest(tenantId);
        return tenantModule.getTenantInternalService().getTenant(getTenantServiceRequest)
                .replaceWithVoid();
    }

    Uni<Void> createUser(Long id, String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id, UserRoleEnum.DEVELOPER, passwordHash);
        final var createUserServiceRequest = new CreateUserInternalRequest(user);
        return userModule.getUserInternalService().createUser(createUserServiceRequest)
                .replaceWithVoid();
    }

    Uni<Void> syncCreateProjectPermission(Long tenantId, Long userId) {
        final var entity = tenantPermissionModelFactory.create(tenantId, userId, TenantPermissionEnum.CREATE_PROJECT);
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionInternalRequest(entity);
        return tenantModule.getTenantInternalService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .replaceWithVoid();
    }
}
