package com.omgservers.service.shard.tenant.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.tenant.tenant.*;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.*;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.*;
import com.omgservers.schema.shard.tenant.tenantImage.*;
import com.omgservers.schema.shard.tenant.tenantPermission.*;
import com.omgservers.schema.shard.tenant.tenantProject.*;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.*;
import com.omgservers.schema.shard.tenant.tenantStage.*;
import com.omgservers.schema.shard.tenant.tenantStagePermission.*;
import com.omgservers.schema.shard.tenant.tenantVersion.*;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import com.omgservers.service.shard.tenant.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.SERVICE})
class TenantApiImpl implements TenantApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantResponse> execute(final GetTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantDataResponse> execute(final GetTenantDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantResponse> execute(final SyncTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantResponse> execute(final DeleteTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> execute(final ViewTenantPermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> execute(
            final VerifyTenantPermissionExistsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> execute(final SyncTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> execute(final DeleteTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> execute(final GetTenantProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> execute(final GetTenantProjectDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantProjectResponse> execute(final SyncTenantProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> execute(ViewTenantProjectsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> execute(final DeleteTenantProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> execute(
            final ViewTenantProjectPermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> execute(
            final VerifyTenantProjectPermissionExistsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::execute);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> execute(
            final SyncTenantProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> execute(
            final DeleteTenantProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> execute(final GetTenantStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantStageDataResponse> execute(final GetTenantStageDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantStageResponse> execute(final SyncTenantStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantStagesResponse> execute(ViewTenantStagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantStageResponse> execute(final DeleteTenantStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> execute(
            final ViewTenantStagePermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> execute(
            final VerifyTenantStagePermissionExistsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> execute(
            final SyncTenantStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantStagePermissionResponse> execute(
            final DeleteTenantStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> execute(final GetTenantVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> execute(final GetTenantVersionConfigRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> execute(final GetTenantVersionDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantVersionResponse> execute(final SyncTenantVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> execute(final ViewTenantVersionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> execute(final DeleteTenantVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> execute(final GetTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindTenantImageResponse> execute(final FindTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantImagesResponse> execute(final ViewTenantImagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantImageResponse> execute(final SyncTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantImageResponse> execute(final DeleteTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantDeploymentResource
     */

    @Override
    public Uni<GetTenantDeploymentResourceResponse> execute(final GetTenantDeploymentResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindTenantDeploymentResourceResponse> execute(final FindTenantDeploymentResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantDeploymentResourcesResponse> execute(final ViewTenantDeploymentResourcesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantDeploymentResourceResponse> execute(final SyncTenantDeploymentResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UpdateTenantDeploymentResourceStatusResponse> execute(final UpdateTenantDeploymentResourceStatusRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentResourceResponse> execute(final DeleteTenantDeploymentResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantDeploymentRef
     */

    @Override
    public Uni<GetTenantDeploymentRefResponse> execute(final GetTenantDeploymentRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindTenantDeploymentRefResponse> execute(final FindTenantDeploymentRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantDeploymentRefsResponse> execute(final ViewTenantDeploymentRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantDeploymentRefResponse> execute(final SyncTenantDeploymentRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentRefResponse> execute(final DeleteTenantDeploymentRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
