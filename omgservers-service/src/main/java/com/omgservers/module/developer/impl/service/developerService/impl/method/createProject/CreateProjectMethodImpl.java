package com.omgservers.module.developer.impl.service.developerService.impl.method.createProject;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.factory.ProjectModelFactory;
import com.omgservers.factory.ProjectPermissionModelFactory;
import com.omgservers.factory.StageModelFactory;
import com.omgservers.factory.StagePermissionModelFactory;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
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

    final ProjectPermissionModelFactory projectPermissionModelFactory;
    final StagePermissionModelFactory stagePermissionModelFactory;
    final ProjectModelFactory projectModelFactory;
    final StageModelFactory stageModelFactory;

    final GenerateIdOperation generateIdOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(final CreateProjectDeveloperRequest request) {
        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();
        return checkCreateProjectPermission(tenantId, userId)
                .flatMap(voidItem -> syncProject(tenantId, userId)
                        .flatMap(project -> syncStage(tenantId, project.getId(), userId)
                                .map(stage -> {
                                    final var projectId = project.getId();
                                    final var stageId = stage.getId();
                                    final var secret = stage.getSecret();
                                    return new CreateProjectDeveloperResponse(projectId, stageId, secret);
                                })));
    }

    Uni<Void> checkCreateProjectPermission(final Long tenantId, final Long userId) {
        // TODO: move to new operation
        final var permission = TenantPermissionEnum.CREATE_PROJECT;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionRequest(tenantId, userId, permission);
        return tenantModule.getTenantService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenantId=%s, userId=%s, permission=%s", tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<ProjectModel> syncProject(final Long tenantId, final Long userId) {
        final var project = projectModelFactory.create(tenantId, ProjectConfigModel.create());
        final var syncProjectInternalRequest = new SyncProjectRequest(project);
        return tenantModule.getProjectService().syncProject(syncProjectInternalRequest)
                .flatMap(response -> syncProjectPermission(tenantId, project.getId(), userId))
                .replaceWith(project);
    }

    Uni<ProjectPermissionModel> syncProjectPermission(final Long tenantId, final Long projectId, final Long userId) {
        final var permission = ProjectPermissionEnum.CREATE_STAGE;
        final var projectPermission = projectPermissionModelFactory.create(tenantId, projectId, userId, permission);
        final var request = new SyncProjectPermissionRequest(projectPermission);
        return tenantModule.getProjectService().syncProjectPermission(request)
                .replaceWith(projectPermission);
    }

    Uni<StageModel> syncStage(final Long tenantId,
                              final Long projectId,
                              final Long userId) {
        final var stage = stageModelFactory.create(tenantId, projectId, new StageConfigModel());
        final var syncStageInternalRequest = new SyncStageRequest(tenantId, stage);
        return tenantModule.getStageService().syncStage(syncStageInternalRequest)
                .flatMap(response -> syncStagePermission(tenantId, stage.getId(), userId))
                .replaceWith(stage);
    }

    Uni<StagePermissionModel> syncStagePermission(final Long tenantId,
                                                  final Long stageId,
                                                  final Long userId) {
        final var permission = StagePermissionEnum.CREATE_VERSION;
        final var stagePermission = stagePermissionModelFactory.create(tenantId, stageId, userId, permission);
        final var request = new SyncStagePermissionRequest(stagePermission);
        return tenantModule.getStageService().syncStagePermission(request)
                .replaceWith(stagePermission);
    }
}
