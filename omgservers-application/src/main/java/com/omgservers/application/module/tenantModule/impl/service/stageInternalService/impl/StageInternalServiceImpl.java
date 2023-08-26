package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl;

import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.GetTenantServiceApiClientOperation;
import com.omgservers.application.module.tenantModule.impl.operation.getTenantServiceApiClientOperation.TenantServiceApiClient;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.StageInternalService;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod.DeleteStageMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.getStageMethod.GetStageMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.hasStagePermissionMethod.HasStagePermissionMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod.SyncStageMethod;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod.SyncStagePermissionMethod;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.DeleteStageRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public Uni<GetStageInternalResponse> getStage(GetStageRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getStage,
                getStageMethod::getStage);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStageRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteStageRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasStagePermissionRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasStagePermission,
                hasStagePermissionMethod::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStagePermissionRoutedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStagePermission,
                syncStagePermissionMethod::syncStagePermission);
    }
}
