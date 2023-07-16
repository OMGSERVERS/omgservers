package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.createNewDeveloperMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CreateDeveloperHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CreateDeveloperHelpResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.GetTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantPermissionInternalRequest;
import com.omgservers.application.module.userModule.impl.service.userInternalService.request.CreateUserInternalRequest;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateDeveloperMethodImpl implements CreateDeveloperMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    @Override
    public Uni<CreateDeveloperHelpResponse> createDeveloper(final CreateDeveloperHelpRequest request) {
        CreateDeveloperHelpRequest.validate(request);
        final var userUuid = UUID.randomUUID();
        // TODO: improve it
        final var password = String.valueOf(Math.abs(new SecureRandom().nextLong()));
        final var tenant = request.getTenant();

        return getTenant(tenant)
                .call(ignored -> createUser(userUuid, password))
                .call(ignored -> syncCreateProjectPermission(tenant, userUuid))
                .replaceWith(new CreateDeveloperHelpResponse(userUuid, password));
    }

    Uni<Void> getTenant(UUID tenant) {
        final var getTenantServiceRequest = new GetTenantInternalRequest(tenant);
        return tenantModule.getTenantInternalService().getTenant(getTenantServiceRequest)
                .replaceWithVoid();
    }

    Uni<Void> createUser(UUID uuid, String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = UserModel.create(uuid, UserRoleEnum.DEVELOPER, passwordHash);
        final var createUserServiceRequest = new CreateUserInternalRequest(user);
        return userModule.getUserInternalService().createUser(createUserServiceRequest)
                .replaceWithVoid();
    }

    Uni<Void> syncCreateProjectPermission(UUID tenant, UUID user) {
        final var entity = TenantPermissionModel.create(tenant, user, TenantPermissionEnum.CREATE_PROJECT);
        final var syncTenantPermissionServiceRequest = new SyncTenantPermissionInternalRequest(entity);
        return tenantModule.getTenantInternalService().syncTenantPermission(syncTenantPermissionServiceRequest)
                .replaceWithVoid();
    }
}
