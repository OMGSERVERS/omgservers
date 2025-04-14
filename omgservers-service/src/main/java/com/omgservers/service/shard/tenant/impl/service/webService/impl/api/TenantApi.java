package com.omgservers.service.shard.tenant.impl.service.webService.impl.api;

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

    /*
    TenantDeploymentRef
     */

    @POST
    @Path("/get-tenant-deployment-ref")
    Uni<GetTenantDeploymentRefResponse> execute(GetTenantDeploymentRefRequest request);

    @POST
    @Path("/find-tenant-deployment-ref")
    Uni<FindTenantDeploymentRefResponse> execute(FindTenantDeploymentRefRequest request);

    @POST
    @Path("/view-tenant-deployment-refs")
    Uni<ViewTenantDeploymentRefsResponse> execute(ViewTenantDeploymentRefsRequest request);

    @POST
    @Path("/sync-tenant-deployment-ref")
    Uni<SyncTenantDeploymentRefResponse> execute(SyncTenantDeploymentRefRequest request);

    @POST
    @Path("/delete-tenant-deployment-ref")
    Uni<DeleteTenantDeploymentRefResponse> execute(DeleteTenantDeploymentRefRequest request);
}
