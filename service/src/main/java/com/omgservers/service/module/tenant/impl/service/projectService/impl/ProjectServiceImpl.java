package com.omgservers.service.module.tenant.impl.service.projectService.impl;

import com.omgservers.model.dto.tenant.DeleteProjectPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteProjectResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.deleteProject.DeleteProjectMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.deleteProjectPermission.DeleteProjectPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.getProject.GetProjectMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.hasProjectPermission.HasProjectPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.syncProject.SyncProjectMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.syncProjectPermission.SyncProjectPermissionMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.projectPermission.viewProjectPermissions.ViewProjectPermissionsMethod;
import com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.viewProjects.ViewProjectsMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.deleteStagePermission.DeleteStagePermissionMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ProjectServiceImpl implements ProjectService {

    final DeleteProjectPermissionMethod deleteProjectPermissionMethod;
    final ViewProjectPermissionsMethod viewProjectPermissionsMethod;
    final DeleteStagePermissionMethod deleteStagePermissionMethod;
    final SyncProjectPermissionMethod syncProjectPermissionMethod;
    final HasProjectPermissionMethod hasProjectPermissionMethod;
    final DeleteProjectMethod deleteProjectMethod;
    final ViewProjectsMethod viewProjectsMethod;
    final SyncProjectMethod syncProjectMethod;
    final GetProjectMethod getProjectMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetProjectResponse> getProject(@Valid final GetProjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getProject,
                getProjectMethod::getProject);
    }

    @Override
    public Uni<SyncProjectResponse> syncProject(@Valid final SyncProjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncProject,
                syncProjectMethod::syncProject);
    }

    @Override
    public Uni<ViewProjectsResponse> viewProjects(@Valid final ViewProjectsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewProjects,
                viewProjectsMethod::viewProjects);
    }

    @Override
    public Uni<DeleteProjectResponse> deleteProject(@Valid final DeleteProjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteProject,
                deleteProjectMethod::deleteProject);
    }

    @Override
    public Uni<ViewProjectPermissionsResponse> viewProjectPermissions(
            @Valid final ViewProjectPermissionsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewProjectPermissions,
                viewProjectPermissionsMethod::viewProjectPermissions);
    }

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(@Valid final HasProjectPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasProjectPermission,
                hasProjectPermissionMethod::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(@Valid final SyncProjectPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncProjectPermission,
                syncProjectPermissionMethod::syncProjectPermission);
    }

    @Override
    public Uni<DeleteProjectPermissionResponse> deleteProjectPermission(
            @Valid final DeleteProjectPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteProjectPermission,
                deleteProjectPermissionMethod::deleteProjectPermission);
    }
}
