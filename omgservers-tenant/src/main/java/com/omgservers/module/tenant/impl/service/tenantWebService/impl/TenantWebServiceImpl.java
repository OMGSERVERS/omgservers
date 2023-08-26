package com.omgservers.module.tenant.impl.service.tenantWebService.impl;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.module.tenant.impl.service.tenantShardedService.TenantShardedService;
import com.omgservers.module.tenant.impl.service.tenantWebService.TenantWebService;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
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
    public Uni<GetTenantResponse> getTenant(GetTenantShardRequest request) {
        return tenantShardedService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request) {
        return tenantShardedService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantShardRequest request) {
        return tenantShardedService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request) {
        return tenantShardedService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request) {
        return tenantShardedService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectInternalResponse> getProject(GetProjectShardRequest request) {
        return projectShardedService.getProject(request);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request) {
        return projectShardedService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(DeleteProjectShardRequest request) {
        return projectShardedService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request) {
        return projectShardedService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request) {
        return projectShardedService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageInternalResponse> getStage(GetStageShardRequest request) {
        return stageShardedService.getStage(request);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request) {
        return stageShardedService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request) {
        return stageShardedService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request) {
        return stageShardedService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request) {
        return stageShardedService.syncStagePermission(request);
    }
}
