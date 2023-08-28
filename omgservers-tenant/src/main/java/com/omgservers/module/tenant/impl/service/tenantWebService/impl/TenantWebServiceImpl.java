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
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
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
    public Uni<GetTenantShardedResponse> getTenant(GetTenantShardedRequest request) {
        return tenantShardedService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request) {
        return tenantShardedService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantShardedRequest request) {
        return tenantShardedService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionShardedResponse> hasTenantPermission(HasTenantPermissionShardedRequest request) {
        return tenantShardedService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request) {
        return tenantShardedService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectShardedResponse> getProject(GetProjectShardedRequest request) {
        return projectShardedService.getProject(request);
    }

    @Override
    public Uni<SyncProjectShardedResponse> syncProject(SyncProjectShardedRequest request) {
        return projectShardedService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(DeleteProjectShardedRequest request) {
        return projectShardedService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionShardedResponse> hasProjectPermission(HasProjectPermissionShardedRequest request) {
        return projectShardedService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionShardedResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request) {
        return projectShardedService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageShardedResponse> getStage(GetStageShardedRequest request) {
        return stageShardedService.getStage(request);
    }

    @Override
    public Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request) {
        return stageShardedService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageShardedResponse> deleteStage(DeleteStageShardedRequest request) {
        return stageShardedService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionShardedResponse> hasStagePermission(HasStagePermissionShardedRequest request) {
        return stageShardedService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionShardedResponse> syncStagePermission(SyncStagePermissionShardedRequest request) {
        return stageShardedService.syncStagePermission(request);
    }
}
