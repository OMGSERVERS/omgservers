package com.omgservers.module.tenant.impl.service.stageShardedService.impl;

import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.deleteStage.DeleteStageMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.getStage.GetStageMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.hasStagePermission.HasStagePermissionMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStage.SyncStageMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStagePermission.SyncStagePermissionMethod;
import com.omgservers.module.tenant.impl.service.stageShardedService.StageShardedService;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageShardedServiceImpl implements StageShardedService {

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    final SyncStagePermissionMethod syncStagePermissionMethod;
    final HasStagePermissionMethod hasStagePermissionMethod;
    final DeleteStageMethod deleteStageMethod;
    final SyncStageMethod syncStageMethod;
    final GetStageMethod getStageMethod;

    @Override
    public Uni<GetStageShardedResponse> getStage(GetStageShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getStage,
                getStageMethod::getStage);
    }

    @Override
    public Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStageShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<DeleteStageShardedResponse> deleteStage(DeleteStageShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteStageShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<HasStagePermissionShardedResponse> hasStagePermission(HasStagePermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                HasStagePermissionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasStagePermission,
                hasStagePermissionMethod::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionShardedResponse> syncStagePermission(SyncStagePermissionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncStagePermissionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncStagePermission,
                syncStagePermissionMethod::syncStagePermission);
    }
}
