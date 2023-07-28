package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectMethod;

import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.model.project.ProjectModelFactory;
import com.omgservers.application.module.tenantModule.model.stage.StageConfigModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModelFactory;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.tenantModule.model.project.ProjectConfigModel;
import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import com.omgservers.application.exception.ServerSideForbiddenException;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.GetTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.HasTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateProjectMethodImpl implements CreateProjectMethod {

    final TenantModule tenantModule;
    final UserModule userModule;

    final ProjectModelFactory projectModelFactory;
    final StageModelFactory stageModelFactory;

    final GenerateIdOperation generateIdOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectHelpResponse> createProject(final CreateProjectHelpRequest request) {
        CreateProjectHelpRequest.validate(request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();

        return checkCreateProjectPermission(tenantId, userId)
                .call(voidItem -> checkTenant(tenantId))
                .flatMap(voidItem -> createProject(tenantId, userId));
    }

    Uni<Void> checkCreateProjectPermission(final Long tenantId, final Long userId) {
        final var permission = TenantPermissionEnum.CREATE_PROJECT;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionInternalRequest(tenantId, userId, permission);
        return tenantModule.getTenantInternalService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenantId=%s, userId=%s, permission=%s", tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> checkTenant(final Long tenantId) {
        final var getTenantServiceRequest = new GetTenantInternalRequest(tenantId);
        return tenantModule.getTenantInternalService().getTenant(getTenantServiceRequest)
                .replaceWithVoid();
    }

    Uni<CreateProjectHelpResponse> createProject(final Long tenantId, final Long userId) {
        final var project = projectModelFactory.create(tenantId, userId, ProjectConfigModel.create());
        final var syncProjectInternalRequest = new SyncProjectInternalRequest(project);
        return tenantModule.getProjectInternalService().syncProject(syncProjectInternalRequest)
                .flatMap(response -> {
                    final var stage = stageModelFactory.create(project.getId(),
                            generateIdOperation.generateId(),
                            new StageConfigModel());
                    final var syncStageInternalRequest = new SyncStageInternalRequest(tenantId, stage);
                    return tenantModule.getStageInternalService().syncStage(syncStageInternalRequest)
                            .replaceWith(new CreateProjectHelpResponse(project.getId(), stage.getId(), stage.getSecret()));
                });
    }
}
