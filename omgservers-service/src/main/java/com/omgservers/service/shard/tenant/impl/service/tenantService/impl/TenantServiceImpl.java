package com.omgservers.service.shard.tenant.impl.service.tenantService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenant.DeleteTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.DeleteTenantResponse;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.shard.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.SyncTenantResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import com.omgservers.schema.shard.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.schema.shard.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.FindTenantImageResponse;
import com.omgservers.schema.shard.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.schema.shard.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.shard.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.SyncTenantProjectResponse;
import com.omgservers.schema.shard.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.shard.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.schema.shard.tenant.tenantStageCommand.DeleteTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.DeleteTenantStageCommandResponse;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.SyncTenantStageCommandResponse;
import com.omgservers.schema.shard.tenant.tenantStageCommand.ViewTenantStageCommandRequest;
import com.omgservers.schema.shard.tenant.tenantStageCommand.ViewTenantStageCommandResponse;
import com.omgservers.schema.shard.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.shard.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.schema.shard.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.shard.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateResponse;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.UpdateTenantStageStateResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.DeleteTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.GetTenantDataMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.GetTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.SyncTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.DeleteTenantDeploymentResourceMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.FindTenantDeploymentResourceMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.GetTenantDeploymentResourceMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.SyncTenantDeploymentResourceMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.ViewTenantDeploymentResourcesMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.DeleteTenantImageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.FindTenantImageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.GetTenantImageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.SyncTenantImageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.ViewTenantImagesMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.DeleteTenantPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.SyncTenantPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.VerifyTenantPermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.ViewTenantPermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.DeleteTenantProjectMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.GetTenantProjectDataMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.GetTenantProjectMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.SyncTenantProjectMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.ViewTenantProjectsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.DeleteTenantProjectPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.SyncTenantProjectPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.VerifyTenantProjectPermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.ViewTenantProjectPermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.DeleteTenantStageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.GetTenantStageDataMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.GetTenantStageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.SyncTenantStageMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.ViewTenantStagesMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand.DeleteTenantStageCommandMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand.SyncTenantStageCommandMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageCommand.ViewTenantStageCommandsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.DeleteTenantStagePermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.SyncTenantStagePermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.VerifyTenantStagePermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.ViewTenantStagePermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageState.GetTenantStageStateMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageState.UpdateTenantStageStateMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.DeleteTenantVersionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.GetTenantVersionConfigMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.GetTenantVersionDataMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.GetTenantVersionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.SyncTenantVersionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.ViewTenantVersionsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantServiceImpl implements TenantService {

    final UpdateTenantDeploymentResourceStatusMethod updateTenantDeploymentResourceStatusMethod;
    final VerifyTenantProjectPermissionExistsMethod verifyTenantProjectPermissionExistsMethod;
    final VerifyTenantStagePermissionExistsMethod verifyTenantStagePermissionExistsMethod;
    final DeleteTenantDeploymentResourceMethod deleteTenantDeploymentResourceMethod;
    final ViewTenantDeploymentResourcesMethod viewTenantDeploymentResourcesMethod;
    final DeleteTenantProjectPermissionMethod deleteTenantProjectPermissionMethod;
    final FindTenantDeploymentResourceMethod findTenantDeploymentResourceMethod;
    final SyncTenantDeploymentResourceMethod syncTenantDeploymentResourceMethod;
    final ViewTenantProjectPermissionsMethod viewTenantProjectPermissionsMethod;
    final VerifyTenantPermissionExistsMethod verifyTenantPermissionExistsMethod;
    final GetTenantDeploymentResourceMethod getTenantDeploymentResourceMethod;
    final DeleteTenantStagePermissionMethod deleteTenantStagePermissionMethod;
    final SyncTenantProjectPermissionMethod syncTenantProjectPermissionMethod;
    final ViewTenantStagePermissionsMethod viewTenantStagePermissionsMethod;
    final SyncTenantStagePermissionMethod syncTenantStagePermissionMethod;
    final DeleteTenantStageCommandMethod deleteTenantStageCommandMethod;
    final ViewTenantStageCommandsMethod viewTenantStageCommandsMethod;
    final UpdateTenantStageStateMethod updateTenantStageStateMethod;
    final SyncTenantStageCommandMethod syncTenantStageCommandMethod;
    final DeleteTenantPermissionMethod deleteTenantPermissionMethod;
    final GetTenantVersionConfigMethod getTenantVersionConfigMethod;
    final ViewTenantPermissionsMethod viewTenantPermissionsMethod;
    final GetTenantProjectDataMethod getTenantProjectDataMethod;
    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final GetTenantVersionDataMethod getTenantVersionDataMethod;
    final GetTenantStageStateMethod getTenantStageStateMethod;
    final DeleteTenantVersionMethod deleteTenantVersionMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final GetTenantStageDataMethod getTenantStageDataMethod;
    final ViewTenantVersionsMethod viewTenantVersionsMethod;
    final ViewTenantProjectsMethod viewTenantProjectsMethod;
    final SyncTenantVersionMethod syncTenantVersionMethod;
    final DeleteTenantStageMethod deleteTenantStageMethod;
    final SyncTenantProjectMethod syncTenantProjectMethod;
    final DeleteTenantImageMethod deleteTenantImageMethod;
    final GetTenantProjectMethod getTenantProjectMethod;
    final GetTenantVersionMethod getTenantVersionMethod;
    final ViewTenantStagesMethod viewTenantStagesMethod;
    final ViewTenantImagesMethod viewTenantImagesMethod;
    final SyncTenantStageMethod syncTenantStageMethod;
    final FindTenantImageMethod findTenantImageMethod;
    final SyncTenantImageMethod syncTenantImageMethod;
    final GetTenantImageMethod getTenantImageMethod;
    final GetTenantStageMethod getTenantStageMethod;
    final GetTenantDataMethod getTenantDataMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final SyncTenantMethod syncTenantMethod;
    final GetTenantMethod getTenantMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantResponse> execute(@Valid final GetTenantRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<GetTenantDataResponse> execute(@Valid final GetTenantDataRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantDataMethod::getTenantData);
    }

    @Override
    public Uni<SyncTenantResponse> execute(@Valid final SyncTenantRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<DeleteTenantResponse> execute(@Valid final DeleteTenantRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantMethod::deleteTenant);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> execute(@Valid final ViewTenantPermissionsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantPermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> execute(
            @Valid final VerifyTenantPermissionExistsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                verifyTenantPermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> execute(@Valid final SyncTenantPermissionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantPermissionMethod::execute);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> execute(
            @Valid final DeleteTenantPermissionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantPermissionMethod::execute);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> execute(@Valid final GetTenantProjectRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantProjectMethod::execute);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> execute(@Valid final GetTenantProjectDataRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantProjectDataMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectResponse> execute(@Valid final SyncTenantProjectRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantProjectMethod::execute);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> execute(@Valid final ViewTenantProjectsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantProjectsMethod::execute);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> execute(@Valid final DeleteTenantProjectRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantProjectMethod::execute);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> execute(
            @Valid final ViewTenantProjectPermissionsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantProjectPermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> execute(
            @Valid final VerifyTenantProjectPermissionExistsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                verifyTenantProjectPermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> execute(
            @Valid final SyncTenantProjectPermissionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantProjectPermissionMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> executeWithIdempotency(
            SyncTenantProjectPermissionRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantProjectPermission(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantProjectPermissionResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> execute(
            @Valid final DeleteTenantProjectPermissionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantProjectPermissionMethod::execute);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> execute(@Valid final GetTenantStageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantStageMethod::execute);
    }

    @Override
    public Uni<GetTenantStageDataResponse> execute(@Valid final GetTenantStageDataRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantStageDataMethod::execute);
    }

    @Override
    public Uni<SyncTenantStageResponse> execute(@Valid final SyncTenantStageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantStageMethod::execute);
    }

    @Override
    public Uni<ViewTenantStagesResponse> execute(@Valid final ViewTenantStagesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantStagesMethod::execute);
    }

    @Override
    public Uni<DeleteTenantStageResponse> execute(@Valid final DeleteTenantStageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantStageMethod::execute);
    }

    /*
    TenantStageCommand
     */

    @Override
    public Uni<ViewTenantStageCommandResponse> execute(@Valid final ViewTenantStageCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantStageCommandsMethod::execute);
    }

    @Override
    public Uni<SyncTenantStageCommandResponse> execute(@Valid final SyncTenantStageCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantStageCommandMethod::execute);
    }

    @Override
    public Uni<SyncTenantStageCommandResponse> executeWithIdempotency(
            @Valid final SyncTenantStageCommandRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantStageCommand(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantStageCommandResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantStageCommandResponse> execute(@Valid final DeleteTenantStageCommandRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantStageCommandMethod::execute);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> execute(
            @Valid final ViewTenantStagePermissionsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantStagePermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> execute(
            @Valid final VerifyTenantStagePermissionExistsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                verifyTenantStagePermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> execute(
            @Valid final SyncTenantStagePermissionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantStagePermissionMethod::execute);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> executeWithIdempotency(
            @Valid final SyncTenantStagePermissionRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantStagePermission(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantStagePermissionResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantStagePermissionResponse> execute(
            @Valid final DeleteTenantStagePermissionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantStagePermissionMethod::execute);
    }

    /*
    TenantStageState
     */

    @Override
    public Uni<GetTenantStageStateResponse> execute(@Valid final GetTenantStageStateRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantStageStateMethod::execute);
    }

    @Override
    public Uni<UpdateTenantStageStateResponse> execute(@Valid final UpdateTenantStageStateRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                updateTenantStageStateMethod::execute);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> execute(@Valid final GetTenantVersionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantVersionMethod::execute);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> execute(
            @Valid final GetTenantVersionConfigRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantVersionConfigMethod::execute);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> execute(GetTenantVersionDataRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantVersionDataMethod::execute);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> execute(@Valid final ViewTenantVersionsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantVersionsMethod::execute);
    }

    @Override
    public Uni<SyncTenantVersionResponse> execute(@Valid final SyncTenantVersionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantVersionMethod::execute);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> execute(@Valid final DeleteTenantVersionRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantVersionMethod::execute);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> execute(@Valid final GetTenantImageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantImageMethod::execute);
    }

    @Override
    public Uni<FindTenantImageResponse> execute(@Valid final FindTenantImageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                findTenantImageMethod::execute);
    }

    @Override
    public Uni<ViewTenantImagesResponse> execute(@Valid final ViewTenantImagesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantImagesMethod::execute);
    }

    @Override
    public Uni<SyncTenantImageResponse> execute(@Valid final SyncTenantImageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantImageMethod::execute);
    }

    @Override
    public Uni<SyncTenantImageResponse> executeWithIdempotency(
            @Valid final SyncTenantImageRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getTenantImage(),
                                    t.getMessage());
                            return Uni.createFrom().item(new SyncTenantImageResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteTenantImageResponse> execute(@Valid final DeleteTenantImageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantImageMethod::execute);
    }

    /*
    TenantDeploymentResource
     */

    @Override
    public Uni<GetTenantDeploymentResourceResponse> execute(
            @Valid final GetTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                getTenantDeploymentResourceMethod::execute);
    }

    @Override
    public Uni<FindTenantDeploymentResourceResponse> execute(@Valid final FindTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                findTenantDeploymentResourceMethod::execute);
    }

    @Override
    public Uni<ViewTenantDeploymentResourcesResponse> execute(
            @Valid final ViewTenantDeploymentResourcesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                viewTenantDeploymentResourcesMethod::execute);
    }

    @Override
    public Uni<SyncTenantDeploymentResourceResponse> execute(@Valid final SyncTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                syncTenantDeploymentResourceMethod::execute);
    }

    @Override
    public Uni<UpdateTenantDeploymentResourceStatusResponse> execute(
            @Valid final UpdateTenantDeploymentResourceStatusRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                updateTenantDeploymentResourceStatusMethod::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentResourceResponse> execute(
            @Valid final DeleteTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getTenantModuleClientOperation::execute,
                TenantModuleClient::execute,
                deleteTenantDeploymentResourceMethod::execute);
    }
}
