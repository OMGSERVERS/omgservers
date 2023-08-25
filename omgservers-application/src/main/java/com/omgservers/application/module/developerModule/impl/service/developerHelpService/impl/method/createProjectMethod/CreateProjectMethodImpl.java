package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectMethod;

import com.omgservers.base.factory.ProjectModelFactory;
import com.omgservers.base.factory.StageModelFactory;
import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
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
    public Uni<CreateProjectDeveloperResponse> createProject(final CreateProjectDeveloperRequest request) {
        CreateProjectDeveloperRequest.validate(request);

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

    Uni<CreateProjectDeveloperResponse> createProject(final Long tenantId, final Long userId) {
        final var project = projectModelFactory.create(tenantId, userId, ProjectConfigModel.create());
        final var syncProjectInternalRequest = new SyncProjectInternalRequest(project);
        return tenantModule.getProjectInternalService().syncProject(syncProjectInternalRequest)
                .flatMap(response -> {
                    final var stage = stageModelFactory.create(project.getId(),
                            generateIdOperation.generateId(),
                            new StageConfigModel());
                    final var syncStageInternalRequest = new SyncStageInternalRequest(tenantId, stage);
                    return tenantModule.getStageInternalService().syncStage(syncStageInternalRequest)
                            .replaceWith(new CreateProjectDeveloperResponse(project.getId(), stage.getId(), stage.getSecret()));
                });
    }
}
