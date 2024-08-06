package com.omgservers.service.module.tenant.impl.service.stageService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.DeleteStagePermissionRequest;
import com.omgservers.schema.module.tenant.DeleteStagePermissionResponse;
import com.omgservers.schema.module.tenant.DeleteStageRequest;
import com.omgservers.schema.module.tenant.DeleteStageResponse;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import com.omgservers.schema.module.tenant.SyncStagePermissionRequest;
import com.omgservers.schema.module.tenant.SyncStagePermissionResponse;
import com.omgservers.schema.module.tenant.SyncStageRequest;
import com.omgservers.schema.module.tenant.SyncStageResponse;
import com.omgservers.schema.module.tenant.ValidateStageSecretRequest;
import com.omgservers.schema.module.tenant.ValidateStageSecretResponse;
import com.omgservers.schema.module.tenant.ViewStagePermissionsRequest;
import com.omgservers.schema.module.tenant.ViewStagePermissionsResponse;
import com.omgservers.schema.module.tenant.ViewStagesRequest;
import com.omgservers.schema.module.tenant.ViewStagesResponse;
import com.omgservers.schema.module.tenant.stage.GetStageDataRequest;
import com.omgservers.schema.module.tenant.stage.GetStageDataResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.deleteStage.DeleteStageMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.getStage.GetStageMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.getStageData.GetStageDataMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.syncStage.SyncStageMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.validateStageSecret.ValidateStageSecretMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.viewStages.ViewStagesMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.deleteStagePermission.DeleteStagePermissionMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.hasStagePermission.HasStagePermissionMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.syncStagePermission.SyncStagePermissionMethod;
import com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stagePermission.viewStagePermissions.ViewStagePermissionsMethod;
import com.omgservers.service.server.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.server.operation.handleInternalRequest.HandleInternalRequestOperation;
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

    final DeleteStagePermissionMethod deleteStagePermissionMethod;
    final ViewStagePermissionsMethod viewStagePermissionsMethod;
    final SyncStagePermissionMethod syncStagePermissionMethod;
    final ValidateStageSecretMethod validateStageSecretMethod;
    final HasStagePermissionMethod hasStagePermissionMethod;
    final GetStageDataMethod getStageDataMethod;
    final DeleteStageMethod deleteStageMethod;
    final ViewStagesMethod viewStagesMethod;
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
    public Uni<GetStageDataResponse> getStageData(@Valid final GetStageDataRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getStageData,
                getStageDataMethod::getStageData);
    }

    @Override
    public Uni<SyncStageResponse> syncStage(@Valid final SyncStageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncStage,
                syncStageMethod::syncStage);
    }

    @Override
    public Uni<ViewStagesResponse> viewStages(@Valid final ViewStagesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewStages,
                viewStagesMethod::viewStages);
    }

    @Override
    public Uni<DeleteStageResponse> deleteStage(@Valid final DeleteStageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteStage,
                deleteStageMethod::deleteStage);
    }

    @Override
    public Uni<ViewStagePermissionsResponse> viewStagePermissions(@Valid final ViewStagePermissionsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewStagePermissions,
                viewStagePermissionsMethod::viewStagePermissions);
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
    public Uni<SyncStagePermissionResponse> syncStagePermissionWithIdempotency(
            @Valid final SyncStagePermissionRequest request) {
        return syncStagePermission(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getPermission(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncStagePermissionResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteStagePermissionResponse> deleteStagePermission(@Valid final DeleteStagePermissionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteStagePermission,
                deleteStagePermissionMethod::deleteStagePermission);
    }

    @Override
    public Uni<ValidateStageSecretResponse> validateStageSecret(@Valid final ValidateStageSecretRequest request) {
        return validateStageSecretMethod.validateStageSecret(request);
    }
}
