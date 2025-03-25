package com.omgservers.service.shard.tenant.impl.service.tenantService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.tenant.tenant.*;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.*;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.*;
import com.omgservers.schema.module.tenant.tenantImage.*;
import com.omgservers.schema.module.tenant.tenantPermission.*;
import com.omgservers.schema.module.tenant.tenantProject.*;
import com.omgservers.schema.module.tenant.tenantProjectPermission.*;
import com.omgservers.schema.module.tenant.tenantStage.*;
import com.omgservers.schema.module.tenant.tenantStagePermission.*;
import com.omgservers.schema.module.tenant.tenantVersion.*;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.shard.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.DeleteTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.GetTenantDataMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.GetTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant.SyncTenantMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.DeleteTenantPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.SyncTenantPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.VerifyTenantPermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantPermission.ViewTenantPermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.DeleteTenantProjectPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.SyncTenantProjectPermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.VerifyTenantProjectPermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProjectPermission.ViewTenantProjectPermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage.*;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.DeleteTenantStagePermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.SyncTenantStagePermissionMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.VerifyTenantStagePermissionExistsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStagePermission.ViewTenantStagePermissionsMethod;
import com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion.*;
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
    final DeleteTenantDeploymentRefMethod deleteTenantDeploymentRefMethod;
    final ViewTenantDeploymentRefsMethod viewTenantDeploymentRefsMethod;
    final FindTenantDeploymentRefMethod findTenantDeploymentRefMethod;
    final SyncTenantDeploymentRefMethod syncTenantDeploymentRefMethod;
    final DeleteTenantPermissionMethod deleteTenantPermissionMethod;
    final GetTenantVersionConfigMethod getTenantVersionConfigMethod;
    final GetTenantDeploymentRefMethod getTenantDeploymentRefMethod;
    final ViewTenantPermissionsMethod viewTenantPermissionsMethod;
    final GetTenantProjectDataMethod getTenantProjectDataMethod;
    final SyncTenantPermissionMethod syncTenantPermissionMethod;
    final GetTenantVersionDataMethod getTenantVersionDataMethod;
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
    final CalculateShardOperation calculateShardOperation;

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantResponse> execute(@Valid final GetTenantRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantMethod::getTenant);
    }

    @Override
    public Uni<GetTenantDataResponse> execute(@Valid final GetTenantDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantDataMethod::getTenantData);
    }

    @Override
    public Uni<SyncTenantResponse> execute(@Valid final SyncTenantRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantMethod::syncTenant);
    }

    @Override
    public Uni<DeleteTenantResponse> execute(@Valid final DeleteTenantRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantMethod::deleteTenant);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> execute(@Valid final ViewTenantPermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantPermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> execute(
            @Valid final VerifyTenantPermissionExistsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                verifyTenantPermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> execute(@Valid final SyncTenantPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantPermissionMethod::execute);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> execute(
            @Valid final DeleteTenantPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantPermissionMethod::execute);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> execute(@Valid final GetTenantProjectRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantProjectMethod::execute);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> execute(@Valid final GetTenantProjectDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantProjectDataMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectResponse> execute(@Valid final SyncTenantProjectRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantProjectMethod::execute);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> execute(@Valid final ViewTenantProjectsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantProjectsMethod::execute);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> execute(@Valid final DeleteTenantProjectRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantProjectMethod::execute);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> execute(
            @Valid final ViewTenantProjectPermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantProjectPermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> execute(
            @Valid final VerifyTenantProjectPermissionExistsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                verifyTenantProjectPermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> execute(
            @Valid final SyncTenantProjectPermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantProjectPermissionMethod::execute);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> execute(@Valid final GetTenantStageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantStageMethod::execute);
    }

    @Override
    public Uni<GetTenantStageDataResponse> execute(@Valid final GetTenantStageDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantStageDataMethod::execute);
    }

    @Override
    public Uni<SyncTenantStageResponse> execute(@Valid final SyncTenantStageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantStageMethod::execute);
    }

    @Override
    public Uni<ViewTenantStagesResponse> execute(@Valid final ViewTenantStagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantStagesMethod::execute);
    }

    @Override
    public Uni<DeleteTenantStageResponse> execute(@Valid final DeleteTenantStageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantStageMethod::execute);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> execute(
            @Valid final ViewTenantStagePermissionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantStagePermissionsMethod::execute);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> execute(
            @Valid final VerifyTenantStagePermissionExistsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                verifyTenantStagePermissionExistsMethod::execute);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> execute(
            @Valid final SyncTenantStagePermissionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantStagePermissionMethod::execute);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> execute(@Valid final GetTenantVersionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantVersionMethod::execute);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> execute(
            @Valid final GetTenantVersionConfigRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantVersionConfigMethod::execute);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> execute(GetTenantVersionDataRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantVersionDataMethod::execute);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> execute(@Valid final ViewTenantVersionsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantVersionsMethod::execute);
    }

    @Override
    public Uni<SyncTenantVersionResponse> execute(@Valid final SyncTenantVersionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantVersionMethod::execute);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> execute(@Valid final DeleteTenantVersionRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantVersionMethod::execute);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> execute(@Valid final GetTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantImageMethod::execute);
    }

    @Override
    public Uni<FindTenantImageResponse> execute(@Valid final FindTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                findTenantImageMethod::execute);
    }

    @Override
    public Uni<ViewTenantImagesResponse> execute(@Valid final ViewTenantImagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantImagesMethod::execute);
    }

    @Override
    public Uni<SyncTenantImageResponse> execute(@Valid final SyncTenantImageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
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
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantImageMethod::execute);
    }

    /*
    TenantDeploymentResource
     */

    @Override
    public Uni<GetTenantDeploymentResourceResponse> execute(
            @Valid final GetTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantDeploymentResourceMethod::execute);
    }

    @Override
    public Uni<FindTenantDeploymentResourceResponse> execute(@Valid final FindTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                findTenantDeploymentResourceMethod::execute);
    }

    @Override
    public Uni<ViewTenantDeploymentResourcesResponse> execute(@Valid final ViewTenantDeploymentResourcesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantDeploymentResourcesMethod::execute);
    }

    @Override
    public Uni<SyncTenantDeploymentResourceResponse> execute(@Valid final SyncTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantDeploymentResourceMethod::execute);
    }

    @Override
    public Uni<UpdateTenantDeploymentResourceStatusResponse> execute(@Valid final UpdateTenantDeploymentResourceStatusRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                updateTenantDeploymentResourceStatusMethod::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentResourceResponse> execute(@Valid final DeleteTenantDeploymentResourceRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantDeploymentResourceMethod::execute);
    }

    /*
    TenantDeploymentRef
     */

    @Override
    public Uni<GetTenantDeploymentRefResponse> execute(@Valid final GetTenantDeploymentRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                getTenantDeploymentRefMethod::execute);
    }

    @Override
    public Uni<FindTenantDeploymentRefResponse> execute(@Valid final FindTenantDeploymentRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                findTenantDeploymentRefMethod::execute);
    }

    @Override
    public Uni<ViewTenantDeploymentRefsResponse> execute(@Valid final ViewTenantDeploymentRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                viewTenantDeploymentRefsMethod::execute);
    }

    @Override
    public Uni<SyncTenantDeploymentRefResponse> execute(@Valid final SyncTenantDeploymentRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                syncTenantDeploymentRefMethod::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentRefResponse> execute(@Valid final DeleteTenantDeploymentRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::execute,
                deleteTenantDeploymentRefMethod::execute);
    }
}
