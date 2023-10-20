package com.omgservers.module.tenant.impl.service.webService.impl;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import com.omgservers.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import com.omgservers.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.module.tenant.impl.service.stageService.StageService;
import com.omgservers.module.tenant.impl.service.tenantService.TenantService;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.module.tenant.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final ProjectService projectService;
    final VersionService versionService;
    final TenantService tenantService;
    final StageService stageService;

    @Override
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        return tenantService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(final SyncTenantRequest request) {
        return tenantService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(final DeleteTenantRequest request) {
        return tenantService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(final HasTenantPermissionRequest request) {
        return tenantService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        return tenantService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectResponse> getProject(final GetProjectRequest request) {
        return projectService.getProject(request);
    }

    @Override
    public Uni<SyncProjectResponse> syncProject(final SyncProjectRequest request) {
        return projectService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(final DeleteProjectRequest request) {
        return projectService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(final HasProjectPermissionRequest request) {
        return projectService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(final SyncProjectPermissionRequest request) {
        return projectService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageResponse> getStage(final GetStageRequest request) {
        return stageService.getStage(request);
    }

    @Override
    public Uni<SyncStageResponse> syncStage(final SyncStageRequest request) {
        return stageService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageResponse> deleteStage(final DeleteStageRequest request) {
        return stageService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionResponse> hasStagePermission(final HasStagePermissionRequest request) {
        return stageService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionResponse> syncStagePermission(final SyncStagePermissionRequest request) {
        return stageService.syncStagePermission(request);
    }

    @Override
    public Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(final SyncVersionMatchmakerRequest request) {
        return versionService.syncVersionMatchmaker(request);
    }

    @Override
    public Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(final FindVersionMatchmakerRequest request) {
        return versionService.findVersionMatchmaker(request);
    }

    @Override
    public Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(final ViewVersionMatchmakersRequest request) {
        return versionService.viewVersionMatchmakers(request);
    }

    @Override
    public Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(final DeleteVersionMatchmakerRequest request) {
        return versionService.deleteVersionMatchmaker(request);
    }

    @Override
    public Uni<SyncVersionRuntimeResponse> syncVersionRuntime(final SyncVersionRuntimeRequest request) {
        return versionService.syncVersionRuntime(request);
    }

    @Override
    public Uni<FindVersionRuntimeResponse> findVersionRuntime(final FindVersionRuntimeRequest request) {
        return versionService.findVersionRuntime(request);
    }

    @Override
    public Uni<SelectVersionRuntimeResponse> selectVersionRuntime(final SelectVersionRuntimeRequest request) {
        return versionService.selectVersionRuntime(request);
    }

    @Override
    public Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(final ViewVersionRuntimesRequest request) {
        return versionService.viewVersionRuntimes(request);
    }

    @Override
    public Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(final DeleteVersionRuntimeRequest request) {
        return versionService.deleteVersionRuntime(request);
    }

    @Override
    public Uni<GetVersionResponse> getVersion(final GetVersionRequest request) {
        return versionService.getVersion(request);
    }

    @Override
    public Uni<SyncVersionResponse> syncVersion(final SyncVersionRequest request) {
        return versionService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionResponse> deleteVersion(final DeleteVersionRequest request) {
        return versionService.deleteVersion(request);
    }

    @Override
    public Uni<GetVersionBytecodeResponse> getBytecode(final GetVersionBytecodeRequest request) {
        return versionService.getVersionBytecode(request);
    }

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(final GetVersionConfigRequest request) {
        return versionService.getVersionConfig(request);
    }

    @Override
    public Uni<FindStageVersionIdResponse> findStageVersionId(final FindStageVersionIdRequest request) {
        return versionService.findStageVersionId(request);
    }
}
