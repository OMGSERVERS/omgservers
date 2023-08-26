package com.omgservers.module.tenant.impl.service.projectShardedService.impl;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient.GetTenantServiceApiClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient.TenantServiceApiClient;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.getProject.GetProjectMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProject.SyncProjectMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.deleteProject.DeleteProjectMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.hasProjectPermission.HasProjectPermissionMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProjectPermission.SyncProjectPermissionMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ProjectShardedServiceImpl implements ProjectShardedService {

    final SyncProjectPermissionMethod syncProjectPermissionMethod;
    final HasProjectPermissionMethod hasProjectPermissionMethod;
    final DeleteProjectMethod deleteProjectMethod;
    final SyncProjectMethod syncProjectMethod;
    final GetProjectMethod getProjectMethod;

    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetProjectInternalResponse> getProject(final GetProjectShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetProjectShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getProject,
                getProjectMethod::getProject);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(final SyncProjectShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncProject,
                syncProjectMethod::syncProject);
    }

    @Override
    public Uni<Void> deleteProject(final DeleteProjectShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteProjectShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteProject,
                deleteProjectMethod::deleteProject);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasProjectPermissionShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasProjectPermission,
                hasProjectPermissionMethod::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectPermissionShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncProjectPermission,
                syncProjectPermissionMethod::syncProjectPermission);
    }
}
