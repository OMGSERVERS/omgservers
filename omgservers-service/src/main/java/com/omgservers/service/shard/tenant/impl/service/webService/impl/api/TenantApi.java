package com.omgservers.service.shard.tenant.impl.service.webService.impl.api;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Tenant Shard API")
@Path("/service/v1/shard/tenant/request")
public interface TenantApi {

    /*
    Tenant
     */

    @POST
    @Path("/get-tenant")
    Uni<GetTenantResponse> execute(GetTenantRequest request);

    @POST
    @Path("/get-tenant-data")
    Uni<GetTenantDataResponse> execute(GetTenantDataRequest request);

    @POST
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> execute(SyncTenantRequest request);

    @POST
    @Path("/delete-tenant")
    Uni<DeleteTenantResponse> execute(DeleteTenantRequest request);

    /*
    TenantPermission
     */

    @POST
    @Path("/view-tenant-permissions")
    Uni<ViewTenantPermissionsResponse> execute(ViewTenantPermissionsRequest request);

    @POST
    @Path("/verify-tenant-permission-exist")
    Uni<VerifyTenantPermissionExistsResponse> execute(VerifyTenantPermissionExistsRequest request);

    @POST
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> execute(SyncTenantPermissionRequest request);

    @POST
    @Path("/delete-tenant-permission")
    Uni<DeleteTenantPermissionResponse> execute(DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    @POST
    @Path("/get-tenant-project")
    Uni<GetTenantProjectResponse> execute(GetTenantProjectRequest request);

    @POST
    @Path("/get-tenant-project-data")
    Uni<GetTenantProjectDataResponse> execute(GetTenantProjectDataRequest request);

    @POST
    @Path("/sync-tenant-project")
    Uni<SyncTenantProjectResponse> execute(SyncTenantProjectRequest request);

    @POST
    @Path("/view-tenant-projects")
    Uni<ViewTenantProjectsResponse> execute(ViewTenantProjectsRequest request);

    @POST
    @Path("/delete-tenant-project")
    Uni<DeleteTenantProjectResponse> execute(DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    @POST
    @Path("/view-tenant-project-permissions")
    Uni<ViewTenantProjectPermissionsResponse> execute(ViewTenantProjectPermissionsRequest request);

    @POST
    @Path("/verify-tenant-project-permission-exists")
    Uni<VerifyTenantProjectPermissionExistsResponse> execute(VerifyTenantProjectPermissionExistsRequest request);

    @POST
    @Path("/sync-tenant-project-permission")
    Uni<SyncTenantProjectPermissionResponse> execute(SyncTenantProjectPermissionRequest request);

    @POST
    @Path("/delete-tenant-project-permission")
    Uni<DeleteTenantProjectPermissionResponse> execute(DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    @POST
    @Path("/get-tenant-stage")
    Uni<GetTenantStageResponse> execute(GetTenantStageRequest request);

    @POST
    @Path("/get-tenant-stage-data")
    Uni<GetTenantStageDataResponse> execute(GetTenantStageDataRequest request);

    @POST
    @Path("/sync-tenant-stage")
    Uni<SyncTenantStageResponse> execute(SyncTenantStageRequest request);

    @POST
    @Path("/view-tenant-stages")
    Uni<ViewTenantStagesResponse> execute(ViewTenantStagesRequest request);

    @POST
    @Path("/delete-tenant-stage")
    Uni<DeleteTenantStageResponse> execute(DeleteTenantStageRequest request);

    /*
    TenantStageCommand
     */

    @POST
    @Path("/view-tenant-stage-commands")
    Uni<ViewTenantStageCommandResponse> execute(ViewTenantStageCommandRequest request);

    @POST
    @Path("/sync-tenant-stage-command")
    Uni<SyncTenantStageCommandResponse> execute(SyncTenantStageCommandRequest request);

    @POST
    @Path("/delete-tenant-stage-command")
    Uni<DeleteTenantStageCommandResponse> execute(DeleteTenantStageCommandRequest request);

    /*
    TenantStagePermission
     */

    @POST
    @Path("/view-tenant-stage-permissions")
    Uni<ViewTenantStagePermissionsResponse> execute(ViewTenantStagePermissionsRequest request);

    @POST
    @Path("/verify-tenant-stage-permission-exists")
    Uni<VerifyTenantStagePermissionExistsResponse> execute(VerifyTenantStagePermissionExistsRequest request);

    @POST
    @Path("/sync-tenant-stage-permission")
    Uni<SyncTenantStagePermissionResponse> execute(SyncTenantStagePermissionRequest request);

    @POST
    @Path("/delete-tenant-stage-permission")
    Uni<DeleteTenantStagePermissionResponse> execute(DeleteTenantStagePermissionRequest request);

    /*
    TenantStageState
     */

    @POST
    @Path("/get-tenant-stage-state")
    Uni<GetTenantStageStateResponse> execute(GetTenantStageStateRequest request);

    @POST
    @Path("/update-tenant-stage-state")
    Uni<UpdateTenantStageStateResponse> execute(UpdateTenantStageStateRequest request);

    /*
    TenantVersion
     */

    @POST
    @Path("/get-tenant-version")
    Uni<GetTenantVersionResponse> execute(GetTenantVersionRequest request);

    @POST
    @Path("/get-tenant-version-config")
    Uni<GetTenantVersionConfigResponse> execute(GetTenantVersionConfigRequest request);

    @POST
    @Path("/get-tenant-version-data")
    Uni<GetTenantVersionDataResponse> execute(GetTenantVersionDataRequest request);

    @POST
    @Path("/sync-tenant-version")
    Uni<SyncTenantVersionResponse> execute(SyncTenantVersionRequest request);

    @POST
    @Path("/view-tenant-versions")
    Uni<ViewTenantVersionsResponse> execute(ViewTenantVersionsRequest request);

    @POST
    @Path("/delete-tenant-version")
    Uni<DeleteTenantVersionResponse> execute(DeleteTenantVersionRequest request);

    /*
    TenantImage
     */

    @POST
    @Path("/get-tenant-image")
    Uni<GetTenantImageResponse> execute(GetTenantImageRequest request);

    @POST
    @Path("/find-tenant-image")
    Uni<FindTenantImageResponse> execute(FindTenantImageRequest request);

    @POST
    @Path("/view-tenant-images")
    Uni<ViewTenantImagesResponse> execute(ViewTenantImagesRequest request);

    @POST
    @Path("/sync-tenant-image")
    Uni<SyncTenantImageResponse> execute(SyncTenantImageRequest request);

    @POST
    @Path("/delete-tenant-image")
    Uni<DeleteTenantImageResponse> execute(DeleteTenantImageRequest request);

    /*
    TenantDeploymentResource
     */

    @POST
    @Path("/get-tenant-deployment-resource")
    Uni<GetTenantDeploymentResourceResponse> execute(GetTenantDeploymentResourceRequest request);

    @POST
    @Path("/find-tenant-deployment-resource")
    Uni<FindTenantDeploymentResourceResponse> execute(FindTenantDeploymentResourceRequest request);

    @POST
    @Path("/view-tenant-deployment-resources")
    Uni<ViewTenantDeploymentResourcesResponse> execute(ViewTenantDeploymentResourcesRequest request);

    @POST
    @Path("/sync-tenant-deployment-resource")
    Uni<SyncTenantDeploymentResourceResponse> execute(SyncTenantDeploymentResourceRequest request);

    @POST
    @Path("/update-tenant-deployment-resource-status")
    Uni<UpdateTenantDeploymentResourceStatusResponse> execute(UpdateTenantDeploymentResourceStatusRequest request);

    @POST
    @Path("/delete-tenant-deployment-resource")
    Uni<DeleteTenantDeploymentResourceResponse> execute(DeleteTenantDeploymentResourceRequest request);
}
