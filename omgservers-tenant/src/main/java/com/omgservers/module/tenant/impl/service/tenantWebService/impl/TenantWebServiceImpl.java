package com.omgservers.module.tenant.impl.service.tenantWebService.impl;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;
import com.omgservers.module.tenant.impl.service.tenantWebService.TenantWebService;
import com.omgservers.dto.tenant.DeleteStageInternalResponse;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectInternalResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectInternalResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageInternalResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantWebServiceImpl implements TenantWebService {

    final TenantShardedService tenantShardedService;
    final ProjectShardedService projectShardedService;
    final StageShardedService stageShardedService;

    @Override
    public Uni<GetTenantResponse> getTenant(GetTenantShardedRequest request) {
        return tenantShardedService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantShardedRequest request) {
        return tenantShardedService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantShardedRequest request) {
        return tenantShardedService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardedRequest request) {
        return tenantShardedService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request) {
        return tenantShardedService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectInternalResponse> getProject(GetProjectShardedRequest request) {
        return projectShardedService.getProject(request);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardedRequest request) {
        return projectShardedService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(DeleteProjectShardedRequest request) {
        return projectShardedService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardedRequest request) {
        return projectShardedService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request) {
        return projectShardedService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageInternalResponse> getStage(GetStageShardedRequest request) {
        return stageShardedService.getStage(request);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageShardedRequest request) {
        return stageShardedService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardedRequest request) {
        return stageShardedService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardedRequest request) {
        return stageShardedService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardedRequest request) {
        return stageShardedService.syncStagePermission(request);
    }
}
