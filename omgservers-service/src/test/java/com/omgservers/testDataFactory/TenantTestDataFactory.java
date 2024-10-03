package com.omgservers.testDataFactory;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.factory.tenant.TenantDeploymentModelFactory;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
import com.omgservers.service.factory.tenant.TenantJenkinsRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRefModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRefModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
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

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantJenkinsRequestModelFactory tenantJenkinsRequestModelFactory;
    final TenantMatchmakerRefModelFactory tenantMatchmakerRefModelFactory;
    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;
    final TenantDeploymentModelFactory tenantDeploymentModelFactory;
    final TenantImageModelFactory tenantImageModelFactory;
    final TenantLobbyRefModelFactory tenantLobbyRefModelFactory;
    final TenantVersionModelFactory tenantVersionModelFactory;
    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantModelFactory tenantModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;

    public TenantModel createTenant() {
        final var tenant = tenantModelFactory.create();
        final var syncTenantRequest = new SyncTenantRequest(tenant);
        tenantService.syncTenant(syncTenantRequest);
        return tenant;
    }

    public TenantProjectModel createTenantProject(final TenantModel tenant) {
        final var tenantProject = tenantProjectModelFactory.create(tenant.getId());
        final var syncTenantProjectRequest = new SyncTenantProjectRequest(tenantProject);
        tenantService.syncTenantProject(syncTenantProjectRequest);
        return tenantProject;
    }

    public TenantStageModel createStage(final TenantProjectModel tenantProject) {
        final var tenantId = tenantProject.getTenantId();
        final var tenantProjectId = tenantProject.getId();
        final var tenantStage = tenantStageModelFactory.create(tenantId, tenantProjectId);
        final var syncTenantStageRequest = new SyncTenantStageRequest(tenantStage);
        tenantService.syncTenantStage(syncTenantStageRequest);
        return tenantStage;
    }

    public TenantVersionModel createTenantVersion(final TenantProjectModel tenantProject) {
        final var tenantId = tenantProject.getTenantId();
        final var tenantProjectId = tenantProject.getId();
        final var tenantVersionConfig = TenantVersionConfigDto.create();
        final var base64Archive = Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8));
        final var tenantVersion = tenantVersionModelFactory.create(tenantId,
                tenantProjectId,
                tenantVersionConfig,
                base64Archive);
        final var syncTenantVersionRequest = new SyncTenantVersionRequest(tenantVersion);
        tenantService.syncTenantVersion(syncTenantVersionRequest);
        return tenantVersion;
    }

    public TenantJenkinsRequestModel createTenantJenkinsRequest(final TenantVersionModel tenantVersion,
                                                                final TenantJenkinsRequestQualifierEnum qualifier,
                                                                final Integer buildNumber) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var versionJenkinsRequest = tenantJenkinsRequestModelFactory.create(tenantId,
                tenantVersionId,
                qualifier,
                buildNumber);
        final var syncTenantJenkinsRequestRequest = new SyncTenantJenkinsRequestRequest(versionJenkinsRequest);
        tenantService.syncTenantJenkinsRequest(syncTenantJenkinsRequestRequest);
        return versionJenkinsRequest;
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

    public TenantLobbyRequestModel createTenantLobbyRequest(final TenantDeploymentModel tenantDeployment) {
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();
        final var tenantLobbyRequest = tenantLobbyRequestModelFactory.create(tenantId,
                tenantDeploymentId);
        final var syncTenantLobbyRequestRequest = new SyncTenantLobbyRequestRequest(tenantLobbyRequest);
        tenantService.syncTenantLobbyRequest(syncTenantLobbyRequestRequest);
        return tenantLobbyRequest;
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

    public TenantMatchmakerRequestModel createTenantMatchmakerRequestModel(
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
