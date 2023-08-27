package com.omgservers.module.tenant.impl.service.projectShardedService.impl;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
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
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectInternalResponse;
import com.omgservers.dto.tenant.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenant.SyncProjectInternalResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionInternalResponse;
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
    public Uni<GetProjectInternalResponse> getProject(final GetProjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetProjectShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getProject,
                getProjectMethod::getProject);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(final SyncProjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncProject,
                syncProjectMethod::syncProject);
    }

    @Override
    public Uni<Void> deleteProject(final DeleteProjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteProjectShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteProject,
                deleteProjectMethod::deleteProject);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasProjectPermissionShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasProjectPermission,
                hasProjectPermissionMethod::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectPermissionShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncProjectPermission,
                syncProjectPermissionMethod::syncProjectPermission);
    }
}
