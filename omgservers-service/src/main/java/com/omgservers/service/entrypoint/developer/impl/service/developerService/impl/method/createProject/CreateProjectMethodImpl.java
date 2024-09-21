package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createProject;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.service.security.ServiceSecurityAttributes;
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

    final TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;
    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;
    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(final CreateProjectDeveloperRequest request) {
        log.debug("Create tenant project, request={}", request);

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
        final var hasTenantPermissionServiceRequest =
                new VerifyTenantPermissionExistsRequest(tenantId, userId, permission);
        return tenantModule.getTenantService().verifyTenantPermissionExists(hasTenantPermissionServiceRequest)
                .map(VerifyTenantPermissionExistsResponse::getExists)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, tenantId=%d, userId=%d, permission=%s",
                                        tenantId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> syncProject(final Long tenantId, final Long userId) {
        final var project = tenantProjectModelFactory.create(tenantId);
        final var syncProjectInternalRequest = new SyncTenantProjectRequest(project);
        return tenantModule.getTenantService().syncTenantProject(syncProjectInternalRequest)
                .flatMap(response -> syncProjectPermission(tenantId, project.getId(), userId))
                .replaceWith(project);
    }

    Uni<TenantProjectPermissionModel> syncProjectPermission(final Long tenantId, final Long projectId,
                                                            final Long userId) {
        final var permission = TenantProjectPermissionEnum.STAGE_MANAGEMENT;
        final var projectPermission =
                tenantProjectPermissionModelFactory.create(tenantId, projectId, userId, permission);
        final var request = new SyncTenantProjectPermissionRequest(projectPermission);
        return tenantModule.getTenantService().syncTenantProjectPermission(request)
                .replaceWith(projectPermission);
    }

    Uni<TenantStageModel> syncStage(final Long tenantId,
                                    final Long projectId,
                                    final Long userId) {
        final var stage = tenantStageModelFactory.create(tenantId, projectId);
        final var syncStageInternalRequest = new SyncTenantStageRequest(stage);
        return tenantModule.getTenantService().syncTenantStage(syncStageInternalRequest)
                .flatMap(response -> syncStageVersionManagementPermission(tenantId, stage.getId(), userId))
                .flatMap(response -> syncStageGettingDashboardPermission(tenantId, stage.getId(), userId))
                .replaceWith(stage);
    }

    Uni<TenantStagePermissionModel> syncStageVersionManagementPermission(final Long tenantId,
                                                                         final Long stageId,
                                                                         final Long userId) {
        final var permission = TenantStagePermissionEnum.VERSION_MANAGEMENT;
        final var stagePermission = tenantStagePermissionModelFactory.create(tenantId, stageId, userId, permission);
        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantModule.getTenantService().syncTenantStagePermission(request)
                .replaceWith(stagePermission);
    }

    Uni<TenantStagePermissionModel> syncStageGettingDashboardPermission(final Long tenantId,
                                                                        final Long stageId,
                                                                        final Long userId) {
        final var permission = TenantStagePermissionEnum.GETTING_DASHBOARD;
        final var stagePermission = tenantStagePermissionModelFactory.create(tenantId, stageId, userId, permission);
        final var request = new SyncTenantStagePermissionRequest(stagePermission);
        return tenantModule.getTenantService().syncTenantStagePermission(request)
                .replaceWith(stagePermission);
    }
}
