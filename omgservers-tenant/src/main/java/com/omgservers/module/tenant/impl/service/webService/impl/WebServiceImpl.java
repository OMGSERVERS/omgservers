package com.omgservers.module.tenant.impl.service.webService.impl;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
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
    public Uni<GetTenantResponse> getTenant(GetTenantRequest request) {
        return tenantService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request) {
        return tenantService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantRequest request) {
        return tenantService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request) {
        return tenantService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request) {
        return tenantService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectResponse> getProject(GetProjectRequest request) {
        return projectService.getProject(request);
    }

    @Override
    public Uni<SyncProjectShardedResponse> syncProject(SyncProjectRequest request) {
        return projectService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(DeleteProjectRequest request) {
        return projectService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request) {
        return projectService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request) {
        return projectService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageResponse> getStage(GetStageRequest request) {
        return stageService.getStage(request);
    }

    @Override
    public Uni<SyncStageResponse> syncStage(SyncStageRequest request) {
        return stageService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request) {
        return stageService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request) {
        return stageService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request) {
        return stageService.syncStagePermission(request);
    }

    @Override
    public Uni<GetVersionResponse> getVersion(GetVersionRequest request) {
        return versionService.getVersion(request);
    }

    @Override
    public Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request) {
        return versionService.syncVersion(request);
    }

    @Override
    public Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request) {
        return versionService.deleteVersion(request);
    }

    @Override
    public Uni<GetVersionBytecodeResponse> getBytecode(GetVersionBytecodeRequest request) {
        return versionService.getVersionBytecode(request);
    }

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request) {
        return versionService.getVersionConfig(request);
    }

    @Override
    public Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request) {
        return versionService.getStageVersionId(request);
    }
}
