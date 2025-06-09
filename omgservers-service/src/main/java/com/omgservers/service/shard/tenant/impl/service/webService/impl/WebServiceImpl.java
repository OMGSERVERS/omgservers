package com.omgservers.service.shard.tenant.impl.service.webService.impl;

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
    TenantStageCommand
     */

    @Override
    public Uni<ViewTenantStageCommandResponse> execute(final ViewTenantStageCommandRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantStageCommandResponse> execute(final SyncTenantStageCommandRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageCommandResponse> execute(final DeleteTenantStageCommandRequest request) {
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
    TenantStageState
     */

    @Override
    public Uni<GetTenantStageStateResponse> execute(final GetTenantStageStateRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<UpdateTenantStageStateResponse> execute(final UpdateTenantStageStateRequest request) {
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
}
