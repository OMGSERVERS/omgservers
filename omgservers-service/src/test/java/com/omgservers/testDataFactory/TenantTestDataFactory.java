package com.omgservers.testDataFactory;

import com.omgservers.schema.model.project.TenantProjectConfigDto;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantConfigDto;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantImage.TenantImageConfigDto;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageConfigDto;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.shard.tenant.service.testInterface.TenantServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantTestDataFactory {

    final TenantServiceTestInterface tenantService;

    final TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;
    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;
    final TenantPermissionModelFactory tenantPermissionModelFactory;
    final TenantVersionModelFactory tenantVersionModelFactory;
    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;
    final TenantImageModelFactory tenantImageModelFactory;
    final TenantModelFactory tenantModelFactory;

    public TenantModel createTenant() {
        final var tenant = tenantModelFactory.create(TenantConfigDto.create());
        final var syncTenantRequest = new SyncTenantRequest(tenant);
        tenantService.syncTenant(syncTenantRequest);
        return tenant;
    }

    public TenantPermissionModel createTenantPermission(final TenantModel tenant,
                                                        final UserModel developerUser,
                                                        final TenantPermissionQualifierEnum permission) {
        final var tenantId = tenant.getId();
        final var userId = developerUser.getId();
        final var tenantPermission = tenantPermissionModelFactory.create(tenantId, userId, permission);
        final var syncTenantPermissionRequest = new SyncTenantPermissionRequest(tenantPermission);
        tenantService.syncTenantPermission(syncTenantPermissionRequest);
        return tenantPermission;
    }

    public TenantProjectModel createTenantProject(final TenantModel tenant) {
        final var tenantProject = tenantProjectModelFactory.create(tenant.getId(), TenantProjectConfigDto.create());
        final var syncTenantProjectRequest = new SyncTenantProjectRequest(tenantProject);
        tenantService.syncTenantProject(syncTenantProjectRequest);
        return tenantProject;
    }

    public TenantProjectPermissionModel createTenantProjectPermission(final TenantProjectModel tenantProject,
                                                                      final UserModel developerUser,
                                                                      final TenantProjectPermissionQualifierEnum permission) {
        final var tenantId = tenantProject.getTenantId();
        final var tenantProjectId = tenantProject.getId();
        final var userId = developerUser.getId();
        final var tenantProjectPermission = tenantProjectPermissionModelFactory
                .create(tenantId, tenantProjectId, userId, permission);
        final var syncTenantProjectPermissionRequest = new SyncTenantProjectPermissionRequest(tenantProjectPermission);
        tenantService.syncTenantProjectPermission(syncTenantProjectPermissionRequest);
        return tenantProjectPermission;
    }

    public TenantStageModel createStage(final TenantProjectModel tenantProject) {
        final var tenantId = tenantProject.getTenantId();
        final var tenantProjectId = tenantProject.getId();
        final var tenantStage = tenantStageModelFactory.create(tenantId,
                tenantProjectId,
                TenantStageConfigDto.create());
        final var syncTenantStageRequest = new SyncTenantStageRequest(tenantStage);
        tenantService.syncTenantStage(syncTenantStageRequest);
        return tenantStage;
    }

    public TenantStagePermissionModel createTenantStagePermission(final TenantStageModel tenantStage,
                                                                  final UserModel developerUser,
                                                                  final TenantStagePermissionQualifierEnum permission) {
        final var tenantId = tenantStage.getTenantId();
        final var tenantStageId = tenantStage.getId();
        final var userId = developerUser.getId();
        final var tenantStagePermission = tenantStagePermissionModelFactory
                .create(tenantId, tenantStageId, userId, permission);
        final var syncTenantStagePermissionRequest = new SyncTenantStagePermissionRequest(tenantStagePermission);
        tenantService.syncTenantStagePermission(syncTenantStagePermissionRequest);
        return tenantStagePermission;
    }

    public TenantVersionModel createTenantVersion(final TenantProjectModel tenantProject) {
        final var tenantId = tenantProject.getTenantId();
        final var tenantProjectId = tenantProject.getId();
        final var tenantVersionConfig = new TenantVersionConfigDto();
        final var tenantVersion = tenantVersionModelFactory.create(tenantId,
                tenantProjectId,
                tenantVersionConfig);
        final var syncTenantVersionRequest = new SyncTenantVersionRequest(tenantVersion);
        tenantService.syncTenantVersion(syncTenantVersionRequest);
        return tenantVersion;
    }

    public TenantImageModel createTenantImage(final TenantVersionModel tenantVersion) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantImage = tenantImageModelFactory.create(tenantId,
                tenantVersionId,
                TenantImageQualifierEnum.UNIVERSAL,
                "universal:latest",
                TenantImageConfigDto.create());
        final var syncTenantImageRequest = new SyncTenantImageRequest(tenantImage);
        tenantService.syncTenantImage(syncTenantImageRequest);
        return tenantImage;
    }
}
