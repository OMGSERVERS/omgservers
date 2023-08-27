package com.omgservers.module.tenant.impl.service.stageShardedService.impl;

import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
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
import com.omgservers.dto.tenant.DeleteStageInternalResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import com.omgservers.dto.tenant.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageInternalResponse;
import com.omgservers.dto.tenant.SyncStagePermissionInternalResponse;
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
    public Uni<GetStageInternalResponse> getStage(GetStageShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::getStage,
                getStageMethod::getStage);
    }

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStageShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteStageShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasStagePermissionShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::hasStagePermission,
                hasStagePermissionMethod::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStagePermissionShardedRequest::validate,
                getTenantServiceApiClientOperation::getClient,
                TenantServiceApiClient::syncStagePermission,
                syncStagePermissionMethod::syncStagePermission);
    }
}
