package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl;

import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.ProjectInternalService;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.deleteProjectMethod.DeleteProjectMethod;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.getProjectMethod.GetProjectMethod;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.hasProjectPermissionMethod.HasProjectPermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectMethod.SyncProjectMethod;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.syncProjectPermissionMethod.SyncProjectPermissionMethod;
import com.omgservers.base.impl.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.DeleteProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ProjectInternalServiceImpl implements ProjectInternalService {

    final SyncProjectPermissionMethod syncProjectPermissionMethod;
    final HasProjectPermissionMethod hasProjectPermissionMethod;
    final DeleteProjectMethod deleteProjectMethod;
    final SyncProjectMethod syncProjectMethod;
    final GetProjectMethod getProjectMethod;

    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetProjectInternalResponse> getProject(final GetProjectInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetProjectInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getProject,
                getProjectMethod::getProject);
    }

    @Override
    public Uni<SyncProjectInternalResponse> syncProject(final SyncProjectInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncProject,
                syncProjectMethod::syncProject);
    }

    @Override
    public Uni<Void> deleteProject(final DeleteProjectInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteProjectInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteProject,
                deleteProjectMethod::deleteProject);
    }

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasProjectPermissionInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasProjectPermission,
                hasProjectPermissionMethod::hasProjectPermission);
    }

    @Override
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncProjectPermissionInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncProjectPermission,
                syncProjectPermissionMethod::syncProjectPermission);
    }
}
