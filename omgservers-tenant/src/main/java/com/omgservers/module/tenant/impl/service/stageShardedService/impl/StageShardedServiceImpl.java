package com.omgservers.module.tenant.impl.service.stageShardedService.impl;

import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient.GetTenantServiceApiClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantServiceApiClient.TenantServiceApiClient;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.deleteStage.DeleteStageMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.getStage.GetStageMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.hasStagePermission.HasStagePermissionMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStage.SyncStageMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStagePermission.SyncStagePermissionMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageShardedServiceImpl implements StageShardedService {

    final GetTenantServiceApiClientOperation getTenantServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncStagePermissionMethod syncStagePermissionMethod;
    final HasStagePermissionMethod hasStagePermissionMethod;
    final DeleteStageMethod deleteStageMethod;
    final SyncStageMethod syncStageMethod;
    final GetStageMethod getStageMethod;

    @Override
    public Uni<GetStageInternalResponse> getStage(GetStageShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getStage,
                getStageMethod::getStage);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStageShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteStageShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasStagePermissionShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasStagePermission,
                hasStagePermissionMethod::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStagePermissionShardRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStagePermission,
                syncStagePermissionMethod::syncStagePermission);
    }
}
