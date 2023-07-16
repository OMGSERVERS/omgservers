package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectDeveloperMethod;

import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.project.ProjectModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import com.omgservers.application.exception.ServerSideForbiddenException;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.CreateProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.GetTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.HasTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateProjectMethodImpl implements CreateProjectMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectHelpResponse> createProject(final CreateProjectHelpRequest request) {
        CreateProjectHelpRequest.validate(request);

        final var user = securityIdentity.<UUID>getAttribute("uuid");
        final var tenant = request.getTenant();

        return checkCreateProjectPermission(tenant, user)
                .call(voidItem -> checkTenant(tenant))
                .flatMap(voidItem -> createProject(tenant, user));
    }

    Uni<Void> checkCreateProjectPermission(final UUID tenant, final UUID user) {
        final var permission = TenantPermissionEnum.CREATE_PROJECT;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionInternalRequest(tenant, user, permission);
        return tenantModule.getTenantInternalService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenant=%s, user=%s, permission=%s", tenant, user, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> checkTenant(final UUID tenant) {
        final var getTenantServiceRequest = new GetTenantInternalRequest(tenant);
        return tenantModule.getTenantInternalService().getTenant(getTenantServiceRequest)
                .replaceWithVoid();
    }

    Uni<CreateProjectHelpResponse> createProject(final UUID tenant, final UUID user) {
        final var project = ProjectModel.create(tenant, user, ProjectConfigModel.create());
        final var stage = StageModel.create(project.getUuid());
        final var request = new CreateProjectInternalRequest(project, stage);
        return tenantModule.getProjectInternalService().createProject(request)
                .replaceWith(new CreateProjectHelpResponse(project.getUuid(), stage.getUuid(), stage.getSecret()));
    }
}
