package com.omgservers.module.tenant.impl.service.projectService.impl;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.projectService.impl.method.getProject.GetProjectMethod;
import com.omgservers.module.tenant.impl.service.projectService.impl.method.syncProject.SyncProjectMethod;
import com.omgservers.module.tenant.impl.service.projectService.ProjectService;
import com.omgservers.module.tenant.impl.service.projectService.impl.method.deleteProject.DeleteProjectMethod;
import com.omgservers.module.tenant.impl.service.projectService.impl.method.hasProjectPermission.HasProjectPermissionMethod;
import com.omgservers.module.tenant.impl.service.projectService.impl.method.syncProjectPermission.SyncProjectPermissionMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ProjectServiceImpl implements ProjectService {

    final SyncProjectPermissionMethod syncProjectPermissionMethod;
    final HasProjectPermissionMethod hasProjectPermissionMethod;
    final DeleteProjectMethod deleteProjectMethod;
    final SyncProjectMethod syncProjectMethod;
    final GetProjectMethod getProjectMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetProjectResponse> getProject(final GetProjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetProjectRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getProject,
                getProjectMethod::getProject);
    }

    @Override
    public Uni<SyncProjectResponse> syncProject(final SyncProjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncProject,
                syncProjectMethod::syncProject);
    }

    @Override
    public Uni<Void> deleteProject(final DeleteProjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteProjectRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteProject,
                deleteProjectMethod::deleteProject);
    }

    @Override
    public Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasProjectPermissionRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasProjectPermission,
                hasProjectPermissionMethod::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectPermissionRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncProjectPermission,
                syncProjectPermissionMethod::syncProjectPermission);
    }
}
