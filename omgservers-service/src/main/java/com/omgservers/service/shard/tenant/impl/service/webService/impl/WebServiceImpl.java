package com.omgservers.service.shard.tenant.impl.service.webService.impl;

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
import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;
import com.omgservers.service.shard.tenant.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final TenantService tenantService;

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantResponse> execute(final GetTenantRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<GetTenantDataResponse> execute(final GetTenantDataRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantResponse> execute(final SyncTenantRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantResponse> execute(final DeleteTenantRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> execute(final ViewTenantPermissionsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> execute(
            final VerifyTenantPermissionExistsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> execute(final SyncTenantPermissionRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> execute(final DeleteTenantPermissionRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> execute(final GetTenantProjectRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> execute(final GetTenantProjectDataRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> execute(final ViewTenantProjectsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantProjectResponse> execute(final SyncTenantProjectRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> execute(final DeleteTenantProjectRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> execute(
            final ViewTenantProjectPermissionsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> execute(
            final VerifyTenantProjectPermissionExistsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> execute(
            final SyncTenantProjectPermissionRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> execute(
            final DeleteTenantProjectPermissionRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> execute(final GetTenantStageRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<GetTenantStageDataResponse> execute(final GetTenantStageDataRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantStagesResponse> execute(final ViewTenantStagesRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantStageResponse> execute(final SyncTenantStageRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageResponse> execute(final DeleteTenantStageRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> execute(
            final ViewTenantStagePermissionsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> execute(
            final VerifyTenantStagePermissionExistsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> execute(
            final SyncTenantStagePermissionRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantStagePermissionResponse> execute(
            final DeleteTenantStagePermissionRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> execute(final GetTenantVersionRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> execute(final GetTenantVersionConfigRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> execute(final GetTenantVersionDataRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> execute(final ViewTenantVersionsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantVersionResponse> execute(final SyncTenantVersionRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> execute(final DeleteTenantVersionRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> execute(final GetTenantImageRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<FindTenantImageResponse> execute(final FindTenantImageRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantImagesResponse> execute(final ViewTenantImagesRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantImageResponse> execute(final SyncTenantImageRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantImageResponse> execute(final DeleteTenantImageRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantDeploymentResource
     */

    @Override
    public Uni<GetTenantDeploymentResourceResponse> execute(GetTenantDeploymentResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<FindTenantDeploymentResourceResponse> execute(FindTenantDeploymentResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantDeploymentResourcesResponse> execute(ViewTenantDeploymentResourcesRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantDeploymentResourceResponse> execute(SyncTenantDeploymentResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<UpdateTenantDeploymentResourceStatusResponse> execute(UpdateTenantDeploymentResourceStatusRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentResourceResponse> execute(DeleteTenantDeploymentResourceRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantDeploymentRef
     */

    @Override
    public Uni<GetTenantDeploymentRefResponse> execute(GetTenantDeploymentRefRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<FindTenantDeploymentRefResponse> execute(FindTenantDeploymentRefRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantDeploymentRefsResponse> execute(ViewTenantDeploymentRefsRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantDeploymentRefResponse> execute(SyncTenantDeploymentRefRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentRefResponse> execute(DeleteTenantDeploymentRefRequest request) {
        return tenantService.execute(request);
    }
}
