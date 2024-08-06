package com.omgservers.testDataFactory;

import com.omgservers.schema.module.tenant.SyncProjectRequest;
import com.omgservers.schema.module.tenant.SyncStageRequest;
import com.omgservers.schema.module.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionRequest;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefQualifierEnum;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.StageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.VersionImageRefModelFactory;
import com.omgservers.service.factory.tenant.VersionJenkinsRequestModelFactory;
import com.omgservers.service.factory.tenant.VersionLobbyRefModelFactory;
import com.omgservers.service.factory.tenant.VersionLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.VersionMatchmakerRefModelFactory;
import com.omgservers.service.factory.tenant.VersionMatchmakerRequestModelFactory;
import com.omgservers.service.factory.tenant.VersionModelFactory;
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

    final VersionMatchmakerRequestModelFactory versionMatchmakerRequestModelFactory;
    final VersionJenkinsRequestModelFactory versionJenkinsRequestModelFactory;
    final VersionMatchmakerRefModelFactory versionMatchmakerRefModelFactory;
    final VersionLobbyRequestModelFactory versionLobbyRequestModelFactory;
    final VersionImageRefModelFactory versionImageRefModelFactory;
    final VersionLobbyRefModelFactory versionLobbyRefModelFactory;
    final VersionModelFactory versionModelFactory;
    final ProjectModelFactory projectModelFactory;
    final TenantModelFactory tenantModelFactory;
    final StageModelFactory stageModelFactory;

    public TenantModel createTenant() {
        final var tenant = tenantModelFactory.create();
        final var syncTenantRequest = new SyncTenantRequest(tenant);
        tenantService.syncTenant(syncTenantRequest);
        return tenant;
    }

    public ProjectModel createProject(final TenantModel tenant) {
        final var project = projectModelFactory.create(tenant.getId());
        final var syncProjectRequest = new SyncProjectRequest(project);
        projectService.syncProject(syncProjectRequest);
        return project;
    }

    public StageModel createStage(final ProjectModel project) {
        final var tenantId = project.getTenantId();
        final var stageId = project.getId();
        final var stage = stageModelFactory.create(tenantId, stageId);
        final var syncStageRequest = new SyncStageRequest(stage);
        stageService.syncStage(syncStageRequest);
        return stage;
    }

    public VersionModel createVersion(final StageModel stage) {
        final var tenantId = stage.getTenantId();
        final var stageId = stage.getId();
        final var versionConfig = VersionConfigDto.create();
        final var base64Archive = Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8));
        final var version = versionModelFactory.create(tenantId,
                stageId,
                versionConfig,
                base64Archive);
        final var syncVersionRequest = new SyncVersionRequest(version);
        versionService.syncVersion(syncVersionRequest);
        return version;
    }

    public VersionJenkinsRequestModel createVersionJenkinsRequest(final VersionModel version,
                                                                  final VersionJenkinsRequestQualifierEnum qualifier,
                                                                  final Integer buildNumber) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionJenkinsRequest = versionJenkinsRequestModelFactory.create(tenantId,
                versionId,
                qualifier,
                buildNumber);
        final var syncVersionJenkinsRequestRequest = new SyncVersionJenkinsRequestRequest(versionJenkinsRequest);
        versionService.syncVersionJenkinsRequest(syncVersionJenkinsRequestRequest);
        return versionJenkinsRequest;
    }

    public VersionImageRefModel createVersionImageRef(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionImageRef = versionImageRefModelFactory.create(tenantId,
                versionId,
                VersionImageRefQualifierEnum.UNIVERSAL,
                "universal:latest");
        final var syncVersionImageRefRequest = new SyncVersionImageRefRequest(versionImageRef);
        versionService.syncVersionImageRef(syncVersionImageRefRequest);
        return versionImageRef;
    }

    public VersionLobbyRequestModel createVersionLobbyRequest(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenantId, versionId);
        final var syncVersionLobbyRequestRequest = new SyncVersionLobbyRequestRequest(versionLobbyRequest);
        versionService.syncVersionLobbyRequest(syncVersionLobbyRequestRequest);
        return versionLobbyRequest;
    }

    public VersionLobbyRefModel createVersionLobbyRef(final VersionModel version, final LobbyModel lobby) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var lobbyId = lobby.getId();
        final var versionLobbyRef = versionLobbyRefModelFactory.create(tenantId, versionId, lobbyId);
        final var syncVersionLobbyRefRequest = new SyncVersionLobbyRefRequest(versionLobbyRef);
        versionService.syncVersionLobbyRef(syncVersionLobbyRefRequest);
        return versionLobbyRef;
    }

    public VersionMatchmakerRequestModel createVersionMatchmakerRequestModel(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionMatchmakerRequest = versionMatchmakerRequestModelFactory.create(tenantId, versionId);
        final var syncVersionMatchmakerRequestRequest =
                new SyncVersionMatchmakerRequestRequest(versionMatchmakerRequest);
        versionService.syncVersionMatchmakerRequest(syncVersionMatchmakerRequestRequest);
        return versionMatchmakerRequest;
    }

    public VersionMatchmakerRefModel createVersionMatchmakerRef(final VersionModel version,
                                                                final MatchmakerModel matchmaker) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var matchmakerId = matchmaker.getId();
        final var versionMatchmakerRef = versionMatchmakerRefModelFactory.create(tenantId, versionId, matchmakerId);
        final var syncVersionMatchmakerRefRequest = new SyncVersionMatchmakerRefRequest(versionMatchmakerRef);
        versionService.syncVersionMatchmakerRef(syncVersionMatchmakerRefRequest);
        return versionMatchmakerRef;
    }
}
