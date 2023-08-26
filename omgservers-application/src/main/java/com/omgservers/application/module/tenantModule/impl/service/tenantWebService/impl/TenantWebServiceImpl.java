package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.ProjectInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.TenantWebService;
import com.omgservers.dto.tenantModule.DeleteProjectRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantRoutedRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionRoutedRequest;
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

    final TenantInternalService tenantInternalService;
    final ProjectInternalService projectInternalService;
    final StageInternalService stageInternalService;

    @Override
    public Uni<GetTenantResponse> getTenant(GetTenantRoutedRequest request) {
        return tenantInternalService.getTenant(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(SyncTenantRoutedRequest request) {
        return tenantInternalService.syncTenant(request);
    }

    @Override
    public Uni<Void> deleteTenant(DeleteTenantRoutedRequest request) {
        return tenantInternalService.deleteTenant(request);
    }

    @Override
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRoutedRequest request) {
        return tenantInternalService.hasTenantPermission(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRoutedRequest request) {
        return tenantInternalService.syncTenantPermission(request);
    }

    @Override
    public Uni<GetProjectInternalResponse> getProject(GetProjectRoutedRequest request) {
        return projectInternalService.getProject(request);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectRoutedRequest request) {
        return projectInternalService.syncProject(request);
    }

    @Override
    public Uni<Void> deleteProject(DeleteProjectRoutedRequest request) {
        return projectInternalService.deleteProject(request);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionRoutedRequest request) {
        return projectInternalService.hasProjectPermission(request);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionRoutedRequest request) {
        return projectInternalService.syncProjectPermission(request);
    }

    @Override
    public Uni<GetStageInternalResponse> getStage(GetStageRoutedRequest request) {
        return stageInternalService.getStage(request);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageRoutedRequest request) {
        return stageInternalService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageRoutedRequest request) {
        return stageInternalService.deleteStage(request);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request) {
        return stageInternalService.hasStagePermission(request);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionRoutedRequest request) {
        return stageInternalService.syncStagePermission(request);
    }
}
