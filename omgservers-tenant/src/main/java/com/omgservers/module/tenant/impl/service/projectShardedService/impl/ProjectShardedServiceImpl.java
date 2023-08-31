package com.omgservers.module.tenant.impl.service.projectShardedService.impl;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.getProject.GetProjectMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProject.SyncProjectMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.ProjectShardedService;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.deleteProject.DeleteProjectMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.hasProjectPermission.HasProjectPermissionMethod;
import com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.syncProjectPermission.SyncProjectPermissionMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedResponse;
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

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetProjectShardedResponse> getProject(final GetProjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetProjectShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getProject,
                getProjectMethod::getProject);
    }

    @Override
    public Uni<SyncProjectShardedResponse> syncProject(final SyncProjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncProject,
                syncProjectMethod::syncProject);
    }

    @Override
    public Uni<Void> deleteProject(final DeleteProjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteProjectShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteProject,
                deleteProjectMethod::deleteProject);
    }

    @Override
    public Uni<HasProjectPermissionShardedResponse> hasProjectPermission(HasProjectPermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasProjectPermissionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasProjectPermission,
                hasProjectPermissionMethod::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionShardedResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectPermissionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncProjectPermission,
                syncProjectPermissionMethod::syncProjectPermission);
    }
}
