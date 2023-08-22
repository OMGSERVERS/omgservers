package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl;

import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.hasStagePermissionMethod.HasStagePermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod.SyncStagePermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod.DeleteStageMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.getStageMethod.GetStageMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod.SyncStageMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.DeleteStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStageInternalResponse;
import com.omgservers.application.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageInternalServiceImpl implements StageInternalService {

    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncStagePermissionMethod syncStagePermissionMethod;
    final HasStagePermissionMethod hasStagePermissionMethod;
    final DeleteStageMethod deleteStageMethod;
    final SyncStageMethod syncStageMethod;
    final GetStageMethod getStageMethod;

    @Override
    public Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getStage,
                getStageMethod::getStage);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStageInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteStageInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasStagePermissionInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasStagePermission,
                hasStagePermissionMethod::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStagePermissionInternalRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStagePermission,
                syncStagePermissionMethod::syncStagePermission);
    }
}
