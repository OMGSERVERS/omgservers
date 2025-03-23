package com.omgservers.testDataFactory;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestQualifierEnum;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.factory.tenant.TenantBuildRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantDeploymentModelFactory;
import com.omgservers.service.factory.tenant.TenantFilesArchiveModelFactory;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRefModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyResourceModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRefModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectPermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantStagePermissionModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.shard.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantTestDataFactory {

    final TenantServiceTestInterface tenantService;

    final TenantProjectPermissionModelFactory tenantProjectPermissionModelFactory;
    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantStagePermissionModelFactory tenantStagePermissionModelFactory;
    final TenantBuildRequestModelFactory tenantBuildRequestModelFactory;
    final TenantMatchmakerRefModelFactory tenantMatchmakerRefModelFactory;
    final TenantLobbyResourceModelFactory tenantLobbyResourceModelFactory;
    final TenantFilesArchiveModelFactory tenantFilesArchiveModelFactory;
    final TenantDeploymentModelFactory tenantDeploymentModelFactory;
    final TenantPermissionModelFactory tenantPermissionModelFactory;
    final TenantLobbyRefModelFactory tenantLobbyRefModelFactory;
    final TenantVersionModelFactory tenantVersionModelFactory;
    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;
    final TenantImageModelFactory tenantImageModelFactory;
    final TenantModelFactory tenantModelFactory;

    public TenantModel createTenant() {
        final var tenant = tenantModelFactory.create();
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
        final var tenantProject = tenantProjectModelFactory.create(tenant.getId());
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
        final var tenantStage = tenantStageModelFactory.create(tenantId, tenantProjectId);
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
        final var tenantVersionConfig = TenantVersionConfigDto.create();
        final var tenantVersion = tenantVersionModelFactory.create(tenantId,
                tenantProjectId,
                tenantVersionConfig);
        final var syncTenantVersionRequest = new SyncTenantVersionRequest(tenantVersion);
        tenantService.syncTenantVersion(syncTenantVersionRequest);
        return tenantVersion;
    }

    public TenantFilesArchiveModel createTenantFilesArchive(final TenantVersionModel tenantVersion) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantFilesArchive = tenantFilesArchiveModelFactory.create(tenantId,
                tenantVersionId,
                Base64.getEncoder().encodeToString("dummy".getBytes(StandardCharsets.UTF_8)));
        final var syncTenantFilesArchiveRequest = new SyncTenantFilesArchiveRequest(tenantFilesArchive);
        tenantService.syncTenantFilesArchive(syncTenantFilesArchiveRequest);
        return tenantFilesArchive;
    }

    public TenantBuildRequestModel createTenantBuildRequest(final TenantVersionModel tenantVersion,
                                                            final TenantBuildRequestQualifierEnum qualifier,
                                                            final Integer buildNumber) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantBuildRequest = tenantBuildRequestModelFactory.create(tenantId,
                tenantVersionId,
                qualifier,
                buildNumber);
        final var syncTenantBuildRequestRequest = new SyncTenantBuildRequestRequest(tenantBuildRequest);
        tenantService.syncTenantBuildRequest(syncTenantBuildRequestRequest);
        return tenantBuildRequest;
    }

    public TenantImageModel createTenantImage(final TenantVersionModel tenantVersion) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantImage = tenantImageModelFactory.create(tenantId,
                tenantVersionId,
                TenantImageQualifierEnum.UNIVERSAL,
                "universal:latest");
        final var syncTenantImageRequest = new SyncTenantImageRequest(tenantImage);
        tenantService.syncTenantImage(syncTenantImageRequest);
        return tenantImage;
    }

    public TenantLobbyResourceModel createTenantLobbyResource(final TenantDeploymentModel tenantDeployment) {
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();
        final var tenantLobbyResource = tenantLobbyResourceModelFactory.create(tenantId,
                tenantDeploymentId);
        final var syncTenantLobbyResourceRequest = new SyncTenantLobbyResourceRequest(tenantLobbyResource);
        tenantService.execute(syncTenantLobbyResourceRequest);
        return tenantLobbyResource;
    }

    public TenantLobbyRefModel createTenantLobbyRef(final TenantDeploymentModel tenantDeployment,
                                                    final LobbyModel lobby) {
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();
        final var lobbyId = lobby.getId();
        final var tenantLobbyRef = tenantLobbyRefModelFactory.create(tenantId,
                tenantDeploymentId,
                lobbyId);
        final var syncTenantLobbyRefRequest = new SyncTenantLobbyRefRequest(tenantLobbyRef);
        tenantService.syncTenantLobbyRef(syncTenantLobbyRefRequest);
        return tenantLobbyRef;
    }

    public TenantMatchmakerRequestModel createTenantMatchmakerRequest(
            final TenantDeploymentModel tenantDeployment) {
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();
        final var versionMatchmakerRequest = tenantMatchmakerRequestModelFactory
                .create(tenantId, tenantDeploymentId);
        final var syncTenantMatchmakerRequestRequest = new SyncTenantMatchmakerRequestRequest(versionMatchmakerRequest);
        tenantService.syncTenantMatchmakerRequest(syncTenantMatchmakerRequestRequest);
        return versionMatchmakerRequest;
    }

    public TenantMatchmakerRefModel createTenantMatchmakerRef(final TenantDeploymentModel tenantDeployment,
                                                              final MatchmakerModel matchmaker) {
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();
        final var matchmakerId = matchmaker.getId();
        final var versionMatchmakerRef = tenantMatchmakerRefModelFactory.create(tenantId,
                tenantDeploymentId,
                matchmakerId);
        final var syncTenantMatchmakerRefRequest = new SyncTenantMatchmakerRefRequest(versionMatchmakerRef);
        tenantService.syncTenantMatchmakerRef(syncTenantMatchmakerRefRequest);
        return versionMatchmakerRef;
    }

    public TenantDeploymentModel createTenantDeployment(final TenantStageModel tenantStage,
                                                        final TenantVersionModel tenantVersion) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantStageId = tenantStage.getId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenantId,
                tenantStageId,
                tenantVersionId);
        final var syncTenantDeploymentRequest = new SyncTenantDeploymentRequest(tenantDeployment);
        tenantService.syncTenantDeployment(syncTenantDeploymentRequest);
        return tenantDeployment;
    }
}
