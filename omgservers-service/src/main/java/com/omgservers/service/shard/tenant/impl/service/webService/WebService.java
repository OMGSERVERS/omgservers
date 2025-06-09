package com.omgservers.service.shard.tenant.impl.service.webService;

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
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Tenant
     */

    Uni<GetTenantResponse> execute(GetTenantRequest request);

    Uni<GetTenantDataResponse> execute(GetTenantDataRequest request);

    Uni<SyncTenantResponse> execute(SyncTenantRequest request);

    Uni<DeleteTenantResponse> execute(DeleteTenantRequest request);

    /*
    TenantPermission
     */

    Uni<ViewTenantPermissionsResponse> execute(ViewTenantPermissionsRequest request);

    Uni<VerifyTenantPermissionExistsResponse> execute(VerifyTenantPermissionExistsRequest request);

    Uni<SyncTenantPermissionResponse> execute(SyncTenantPermissionRequest request);

    Uni<DeleteTenantPermissionResponse> execute(DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    Uni<GetTenantProjectResponse> execute(GetTenantProjectRequest request);

    Uni<GetTenantProjectDataResponse> execute(GetTenantProjectDataRequest request);

    Uni<SyncTenantProjectResponse> execute(SyncTenantProjectRequest request);

    Uni<ViewTenantProjectsResponse> execute(ViewTenantProjectsRequest request);

    Uni<DeleteTenantProjectResponse> execute(DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    Uni<ViewTenantProjectPermissionsResponse> execute(ViewTenantProjectPermissionsRequest request);

    Uni<VerifyTenantProjectPermissionExistsResponse> execute(VerifyTenantProjectPermissionExistsRequest request);

    Uni<SyncTenantProjectPermissionResponse> execute(SyncTenantProjectPermissionRequest request);

    Uni<DeleteTenantProjectPermissionResponse> execute(DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    Uni<GetTenantStageResponse> execute(GetTenantStageRequest request);

    Uni<GetTenantStageDataResponse> execute(GetTenantStageDataRequest request);

    Uni<SyncTenantStageResponse> execute(SyncTenantStageRequest request);

    Uni<ViewTenantStagesResponse> execute(ViewTenantStagesRequest request);

    Uni<DeleteTenantStageResponse> execute(DeleteTenantStageRequest request);

    /*
    TenantStageCommand
     */

    Uni<ViewTenantStageCommandResponse> execute(ViewTenantStageCommandRequest request);

    Uni<SyncTenantStageCommandResponse> execute(SyncTenantStageCommandRequest request);

    Uni<DeleteTenantStageCommandResponse> execute(DeleteTenantStageCommandRequest request);

    /*
    TenantStagePermission
     */

    Uni<ViewTenantStagePermissionsResponse> execute(ViewTenantStagePermissionsRequest request);

    Uni<VerifyTenantStagePermissionExistsResponse> execute(VerifyTenantStagePermissionExistsRequest request);

    Uni<SyncTenantStagePermissionResponse> execute(SyncTenantStagePermissionRequest request);

    Uni<DeleteTenantStagePermissionResponse> execute(DeleteTenantStagePermissionRequest request);

    /*
    TenantStageState
     */

    Uni<GetTenantStageStateResponse> execute(GetTenantStageStateRequest request);

    Uni<UpdateTenantStageStateResponse> execute(UpdateTenantStageStateRequest request);

    /*
    TenantVersion
     */

    Uni<GetTenantVersionResponse> execute(GetTenantVersionRequest request);

    Uni<GetTenantVersionConfigResponse> execute(GetTenantVersionConfigRequest request);

    Uni<GetTenantVersionDataResponse> execute(GetTenantVersionDataRequest request);

    Uni<SyncTenantVersionResponse> execute(SyncTenantVersionRequest request);

    Uni<ViewTenantVersionsResponse> execute(ViewTenantVersionsRequest request);

    Uni<DeleteTenantVersionResponse> execute(DeleteTenantVersionRequest request);

    /*
    TenantImage
     */

    Uni<GetTenantImageResponse> execute(GetTenantImageRequest request);

    Uni<FindTenantImageResponse> execute(FindTenantImageRequest request);

    Uni<ViewTenantImagesResponse> execute(ViewTenantImagesRequest request);

    Uni<SyncTenantImageResponse> execute(SyncTenantImageRequest request);

    Uni<DeleteTenantImageResponse> execute(DeleteTenantImageRequest request);

    /*
    TenantDeploymentResource
     */

    Uni<GetTenantDeploymentResourceResponse> execute(GetTenantDeploymentResourceRequest request);

    Uni<FindTenantDeploymentResourceResponse> execute(FindTenantDeploymentResourceRequest request);

    Uni<ViewTenantDeploymentResourcesResponse> execute(ViewTenantDeploymentResourcesRequest request);

    Uni<SyncTenantDeploymentResourceResponse> execute(SyncTenantDeploymentResourceRequest request);

    Uni<UpdateTenantDeploymentResourceStatusResponse> execute(UpdateTenantDeploymentResourceStatusRequest request);

    Uni<DeleteTenantDeploymentResourceResponse> execute(DeleteTenantDeploymentResourceRequest request);
}
