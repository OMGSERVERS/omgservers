package com.omgservers.module.tenant.impl.service.stageService.impl;

import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.tenant.ValidateStageSecretResponse;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.stageService.StageService;
import com.omgservers.module.tenant.impl.service.stageService.impl.method.deleteStage.DeleteStageMethod;
import com.omgservers.module.tenant.impl.service.stageService.impl.method.getStage.GetStageMethod;
import com.omgservers.module.tenant.impl.service.stageService.impl.method.hasStagePermission.HasStagePermissionMethod;
import com.omgservers.module.tenant.impl.service.stageService.impl.method.syncStage.SyncStageMethod;
import com.omgservers.module.tenant.impl.service.stageService.impl.method.syncStagePermission.SyncStagePermissionMethod;
import com.omgservers.module.tenant.impl.service.stageService.impl.method.validateStageSecret.ValidateStageSecretMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageServiceImpl implements StageService {

    final SyncStagePermissionMethod syncStagePermissionMethod;
    final ValidateStageSecretMethod validateStageSecretMethod;
    final HasStagePermissionMethod hasStagePermissionMethod;
    final DeleteStageMethod deleteStageMethod;
    final SyncStageMethod syncStageMethod;
    final GetStageMethod getStageMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetStageResponse> getStage(@Valid final GetStageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getStage,
                getStageMethod::getStage);
    }

    @Override
    public Uni<SyncStageResponse> syncStage(@Valid final SyncStageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<DeleteStageResponse> deleteStage(@Valid final DeleteStageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<HasStagePermissionResponse> hasStagePermission(@Valid final HasStagePermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::hasStagePermission,
                hasStagePermissionMethod::hasStagePermission);
    }

    @Override
    public Uni<SyncStagePermissionResponse> syncStagePermission(@Valid final SyncStagePermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncStagePermission,
                syncStagePermissionMethod::syncStagePermission);
    }

    @Override
    public Uni<ValidateStageSecretResponse> validateStageSecret(@Valid final ValidateStageSecretRequest request) {
        return validateStageSecretMethod.validateStageSecret(request);
    }
}
