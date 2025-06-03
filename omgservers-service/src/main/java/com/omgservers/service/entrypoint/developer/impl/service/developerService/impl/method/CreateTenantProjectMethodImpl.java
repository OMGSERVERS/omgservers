package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.model.project.TenantProjectConfigDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CreateTenantProjectPermissionOperation;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CreateTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.operation.authz.AuthorizeTenantRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTenantProjectMethodImpl implements CreateTenantProjectMethod {

    final TenantShard tenantShard;

    final CreateTenantProjectPermissionOperation createTenantProjectPermissionOperation;
    final CreateTenantStagePermissionOperation createTenantStagePermissionOperation;
    final AuthorizeTenantRequestOperation authorizeTenantRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;



    @Override
    public Uni<CreateProjectDeveloperResponse> execute(final CreateProjectDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantPermissionQualifierEnum.PROJECT_MANAGER;

        return authorizeTenantRequestOperation.execute(tenant, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();

                    return createTenantProject(tenantId, userId)
                            .flatMap(tenantProject -> createTenantStage(tenantId, tenantProject.getId(), userId)
                                    .map(tenantStage -> {
                                        final var tenantProjectId = tenantProject.getId();
                                        final var tenantStageId = tenantStage.getId();

                                        log.info("Created project \"{}\" in tenant \"{}\"",
                                                tenantProjectId, tenantId);

                                        return new CreateProjectDeveloperResponse(tenantProjectId,
                                                tenantStageId);
                                    }));
                });
    }

    Uni<TenantProjectModel> createTenantProject(final Long tenantId, final Long userId) {
        final var tenantProject = tenantProjectModelFactory.create(tenantId, TenantProjectConfigDto.create());
        final var tenantProjectId = tenantProject.getId();
        final var request = new SyncTenantProjectRequest(tenantProject);
        return tenantShard.getService().execute(request)
                .flatMap(response -> createTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId,
                        TenantProjectPermissionQualifierEnum.STAGE_MANAGER))
                .flatMap(response -> createTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId,
                        TenantProjectPermissionQualifierEnum.VERSION_MANAGER))
                .flatMap(response -> createTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId,
                        TenantProjectPermissionQualifierEnum.PROJECT_VIEWER))
                .replaceWith(tenantProject);
    }

    Uni<TenantStageModel> createTenantStage(final Long tenantId,
                                            final Long tenantProjectId,
                                            final Long userId) {
        final var tenantStage = tenantStageModelFactory.create(tenantId, tenantProjectId, new TenantStageConfigDto());
        final var tenantStageId = tenantStage.getId();
        final var request = new SyncTenantStageRequest(tenantStage);
        return tenantShard.getService().execute(request)
                .flatMap(response -> createTenantStagePermissionOperation.execute(tenantId, tenantStageId, userId,
                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER))
                .replaceWith(tenantStage);
    }
}
