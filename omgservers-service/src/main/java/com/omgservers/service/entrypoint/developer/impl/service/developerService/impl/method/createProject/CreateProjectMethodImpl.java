package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createProject;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.stagePermission.StagePermissionEnum;
import com.omgservers.schema.model.stagePermission.StagePermissionModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.schema.module.tenant.HasTenantPermissionRequest;
import com.omgservers.schema.module.tenant.HasTenantPermissionResponse;
import com.omgservers.schema.module.tenant.SyncProjectPermissionRequest;
import com.omgservers.schema.module.tenant.SyncProjectRequest;
import com.omgservers.schema.module.tenant.SyncStagePermissionRequest;
import com.omgservers.schema.module.tenant.SyncStageRequest;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.ProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.StageModelFactory;
import com.omgservers.service.factory.tenant.StagePermissionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.server.operation.generateId.GenerateIdOperation;
import com.omgservers.service.server.security.ServiceSecurityAttributes;
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

    final GenerateIdOperation generateIdOperation;

    final ProjectPermissionModelFactory projectPermissionModelFactory;
    final StagePermissionModelFactory stagePermissionModelFactory;
    final ProjectModelFactory projectModelFactory;
    final StageModelFactory stageModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(final CreateProjectDeveloperRequest request) {
        log.debug("Create project, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());
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
        final var permission = TenantPermissionEnum.PROJECT_MANAGEMENT;
        final var hasTenantPermissionServiceRequest = new HasTenantPermissionRequest(tenantId, userId, permission);
        return tenantModule.getTenantService().hasTenantPermission(hasTenantPermissionServiceRequest)
                .map(HasTenantPermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, tenantId=%d, userId=%d, permission=%s",
                                        tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<ProjectModel> syncProject(final Long tenantId, final Long userId) {
        final var project = projectModelFactory.create(tenantId);
        final var syncProjectInternalRequest = new SyncProjectRequest(project);
        return tenantModule.getProjectService().syncProject(syncProjectInternalRequest)
                .flatMap(response -> syncProjectPermission(tenantId, project.getId(), userId))
                .replaceWith(project);
    }

    Uni<ProjectPermissionModel> syncProjectPermission(final Long tenantId, final Long projectId, final Long userId) {
        final var permission = ProjectPermissionEnum.STAGE_MANAGEMENT;
        final var projectPermission = projectPermissionModelFactory.create(tenantId, projectId, userId, permission);
        final var request = new SyncProjectPermissionRequest(projectPermission);
        return tenantModule.getProjectService().syncProjectPermission(request)
                .replaceWith(projectPermission);
    }

    Uni<StageModel> syncStage(final Long tenantId,
                              final Long projectId,
                              final Long userId) {
        final var stage = stageModelFactory.create(tenantId, projectId);
        final var syncStageInternalRequest = new SyncStageRequest(stage);
        return tenantModule.getStageService().syncStage(syncStageInternalRequest)
                .flatMap(response -> syncStageVersionManagementPermission(tenantId, stage.getId(), userId))
                .flatMap(response -> syncStageGettingDashboardPermission(tenantId, stage.getId(), userId))
                .replaceWith(stage);
    }

    Uni<StagePermissionModel> syncStageVersionManagementPermission(final Long tenantId,
                                                                   final Long stageId,
                                                                   final Long userId) {
        final var permission = StagePermissionEnum.VERSION_MANAGEMENT;
        final var stagePermission = stagePermissionModelFactory.create(tenantId, stageId, userId, permission);
        final var request = new SyncStagePermissionRequest(stagePermission);
        return tenantModule.getStageService().syncStagePermission(request)
                .replaceWith(stagePermission);
    }

    Uni<StagePermissionModel> syncStageGettingDashboardPermission(final Long tenantId,
                                                                  final Long stageId,
                                                                  final Long userId) {
        final var permission = StagePermissionEnum.GETTING_DASHBOARD;
        final var stagePermission = stagePermissionModelFactory.create(tenantId, stageId, userId, permission);
        final var request = new SyncStagePermissionRequest(stagePermission);
        return tenantModule.getStageService().syncStagePermission(request)
                .replaceWith(stagePermission);
    }
}
