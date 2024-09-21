package com.omgservers.testDataFactory;

import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefQualifierEnum;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantImageRefModelFactory;
import com.omgservers.service.factory.tenant.TenantJenkinsRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRefModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRefModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.impl.service.projectService.testInterface.ProjectServiceTestInterface;
import com.omgservers.service.module.tenant.impl.service.stageService.testInterface.StageServiceTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.service.module.tenant.impl.service.versionService.testInterface.VersionServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantTestDataFactory {

    final ProjectServiceTestInterface projectService;
    final TenantServiceTestInterface tenantService;
    final StageServiceTestInterface stageService;
    final VersionServiceTestInterface versionService;

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantJenkinsRequestModelFactory tenantJenkinsRequestModelFactory;
    final TenantMatchmakerRefModelFactory tenantMatchmakerRefModelFactory;
    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;
    final TenantImageRefModelFactory tenantImageRefModelFactory;
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

    public TenantProjectModel createProject(final TenantModel tenant) {
        final var project = tenantProjectModelFactory.create(tenant.getId());
        final var syncProjectRequest = new SyncTenantProjectRequest(project);
        projectService.syncProject(syncProjectRequest);
        return project;
    }

    public TenantStageModel createStage(final TenantProjectModel project) {
        final var tenantId = project.getTenantId();
        final var stageId = project.getId();
        final var stage = tenantStageModelFactory.create(tenantId, stageId);
        final var syncStageRequest = new SyncTenantStageRequest(stage);
        stageService.syncStage(syncStageRequest);
        return stage;
    }

    public TenantVersionModel createVersion(final TenantStageModel stage) {
        final var tenantId = stage.getTenantId();
        final var stageId = stage.getId();
        final var versionConfig = TenantVersionConfigDto.create();
        final var base64Archive = Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8));
        final var version = tenantVersionModelFactory.create(tenantId,
                stageId,
                versionConfig,
                base64Archive);
        final var syncVersionRequest = new SyncTenantVersionRequest(version);
        versionService.syncVersion(syncVersionRequest);
        return version;
    }

    public TenantJenkinsRequestModel createVersionJenkinsRequest(final TenantVersionModel version,
                                                                 final TenantJenkinsRequestQualifierEnum qualifier,
                                                                 final Integer buildNumber) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionJenkinsRequest = tenantJenkinsRequestModelFactory.create(tenantId,
                versionId,
                qualifier,
                buildNumber);
        final var syncVersionJenkinsRequestRequest = new SyncTenantJenkinsRequestRequest(versionJenkinsRequest);
        versionService.syncVersionJenkinsRequest(syncVersionJenkinsRequestRequest);
        return versionJenkinsRequest;
    }

    public TenantImageRefModel createVersionImageRef(final TenantVersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionImageRef = tenantImageRefModelFactory.create(tenantId,
                versionId,
                TenantImageRefQualifierEnum.UNIVERSAL,
                "universal:latest");
        final var syncVersionImageRefRequest = new SyncTenantImageRefRequest(versionImageRef);
        versionService.syncVersionImageRef(syncVersionImageRefRequest);
        return versionImageRef;
    }

    public TenantLobbyRequestModel createVersionLobbyRequest(final TenantVersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionLobbyRequest = tenantLobbyRequestModelFactory.create(tenantId, versionId);
        final var syncVersionLobbyRequestRequest = new SyncTenantLobbyRequestRequest(versionLobbyRequest);
        versionService.syncVersionLobbyRequest(syncVersionLobbyRequestRequest);
        return versionLobbyRequest;
    }

    public TenantLobbyRefModel createVersionLobbyRef(final TenantVersionModel version, final LobbyModel lobby) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var lobbyId = lobby.getId();
        final var versionLobbyRef = tenantLobbyRefModelFactory.create(tenantId, versionId, lobbyId);
        final var syncVersionLobbyRefRequest = new SyncTenantLobbyRefRequest(versionLobbyRef);
        versionService.syncVersionLobbyRef(syncVersionLobbyRefRequest);
        return versionLobbyRef;
    }

    public TenantMatchmakerRequestModel createVersionMatchmakerRequestModel(final TenantVersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionMatchmakerRequest = tenantMatchmakerRequestModelFactory.create(tenantId, versionId);
        final var syncVersionMatchmakerRequestRequest =
                new SyncTenantMatchmakerRequestRequest(versionMatchmakerRequest);
        versionService.syncVersionMatchmakerRequest(syncVersionMatchmakerRequestRequest);
        return versionMatchmakerRequest;
    }

    public TenantMatchmakerRefModel createVersionMatchmakerRef(final TenantVersionModel version,
                                                               final MatchmakerModel matchmaker) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var matchmakerId = matchmaker.getId();
        final var versionMatchmakerRef = tenantMatchmakerRefModelFactory.create(tenantId, versionId, matchmakerId);
        final var syncVersionMatchmakerRefRequest = new SyncTenantMatchmakerRefRequest(versionMatchmakerRef);
        versionService.syncVersionMatchmakerRef(syncVersionMatchmakerRefRequest);
        return versionMatchmakerRef;
    }
}
