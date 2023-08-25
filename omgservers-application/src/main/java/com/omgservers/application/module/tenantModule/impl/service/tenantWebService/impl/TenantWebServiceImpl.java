package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl;

import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.ProjectInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.TenantInternalService;
import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.TenantWebService;
import com.omgservers.dto.tenantModule.DeleteProjectInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageInternalRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionInternalRequest;
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
    public Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request) {
        return stageInternalService.syncStage(request);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request) {
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
