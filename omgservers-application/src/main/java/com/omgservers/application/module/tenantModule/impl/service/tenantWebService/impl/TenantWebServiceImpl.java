package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.ProjectInternalService;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.GetProjectInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.HasProjectPermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectPermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.GetTenantResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.TenantWebService;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantWebServiceImpl implements TenantWebService {

    final TenantInternalService tenantInternalService;
    final ProjectInternalService projectInternalService;
    final StageInternalService stageInternalService;

    @Override
    public Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request) {
        return tenantInternalService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request) {
        return tenantInternalService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantInternalRequest request) {
        return tenantInternalService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request) {
        return tenantInternalService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request) {
        return tenantInternalService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request) {
        return projectInternalService.getProject(request);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request) {
        return projectInternalService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(DeleteProjectInternalRequest request) {
        return projectInternalService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request) {
        return projectInternalService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request) {
        return projectInternalService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request) {
        return stageInternalService.getStage(request);
    }

    @Override
    public Uni<Void> syncStage(SyncStageInternalRequest request) {
        return stageInternalService.syncStage(request);
    }

    @Override
    public Uni<Void> deleteStage(DeleteStageInternalRequest request) {
        return stageInternalService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request) {
        return stageInternalService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request) {
        return stageInternalService.syncStagePermission(request);
    }
}
