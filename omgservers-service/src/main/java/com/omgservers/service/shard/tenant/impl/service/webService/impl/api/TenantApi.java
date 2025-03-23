package com.omgservers.service.shard.tenant.impl.service.webService.impl.api;

import com.omgservers.schema.module.tenant.tenant.*;
import com.omgservers.schema.module.tenant.tenantBuildRequest.*;
import com.omgservers.schema.module.tenant.tenantDeployment.*;
import com.omgservers.schema.module.tenant.tenantFilesArchive.*;
import com.omgservers.schema.module.tenant.tenantImage.*;
import com.omgservers.schema.module.tenant.tenantLobbyRef.*;
import com.omgservers.schema.module.tenant.tenantLobbyResource.*;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.*;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.*;
import com.omgservers.schema.module.tenant.tenantPermission.*;
import com.omgservers.schema.module.tenant.tenantProject.*;
import com.omgservers.schema.module.tenant.tenantProjectPermission.*;
import com.omgservers.schema.module.tenant.tenantStage.*;
import com.omgservers.schema.module.tenant.tenantStagePermission.*;
import com.omgservers.schema.module.tenant.tenantVersion.*;
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
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    @POST
    @Path("/get-tenant-data")
    Uni<GetTenantDataResponse> getTenantData(GetTenantDataRequest request);

    @POST
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    @POST
    @Path("/delete-tenant")
    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);

    /*
    TenantPermission
     */

    @POST
    @Path("/view-tenant-permissions")
    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(ViewTenantPermissionsRequest request);

    @POST
    @Path("/verify-tenant-permission-exist")
    Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(VerifyTenantPermissionExistsRequest request);

    @POST
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    @POST
    @Path("/delete-tenant-permission")
    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    @POST
    @Path("/get-tenant-project")
    Uni<GetTenantProjectResponse> getTenantProject(GetTenantProjectRequest request);

    @POST
    @Path("/get-tenant-project-data")
    Uni<GetTenantProjectDataResponse> getTenantProjectData(GetTenantProjectDataRequest request);

    @POST
    @Path("/sync-tenant-project")
    Uni<SyncTenantProjectResponse> syncTenantProject(SyncTenantProjectRequest request);

    @POST
    @Path("/view-tenant-projects")
    Uni<ViewTenantProjectsResponse> viewTenantProjects(ViewTenantProjectsRequest request);

    @POST
    @Path("/delete-tenant-project")
    Uni<DeleteTenantProjectResponse> deleteTenantProject(DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    @POST
    @Path("/view-tenant-project-permissions")
    Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(ViewTenantProjectPermissionsRequest request);

    @POST
    @Path("/verify-tenant-project-permission-exists")
    Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            VerifyTenantProjectPermissionExistsRequest request);

    @POST
    @Path("/sync-tenant-project-permission")
    Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(SyncTenantProjectPermissionRequest request);

    @POST
    @Path("/delete-tenant-project-permission")
    Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    @POST
    @Path("/get-tenant-stage")
    Uni<GetTenantStageResponse> getTenantStage(GetTenantStageRequest request);

    @POST
    @Path("/get-tenant-stage-data")
    Uni<GetTenantStageDataResponse> getTenantStageData(GetTenantStageDataRequest request);

    @POST
    @Path("/sync-tenant-stage")
    Uni<SyncTenantStageResponse> syncTenantStage(SyncTenantStageRequest request);

    @POST
    @Path("/view-tenant-stages")
    Uni<ViewTenantStagesResponse> viewTenantStages(ViewTenantStagesRequest request);

    @POST
    @Path("/delete-tenant-stage")
    Uni<DeleteTenantStageResponse> deleteTenantStage(DeleteTenantStageRequest request);

    /*
    TenantStagePermission
     */

    @POST
    @Path("/view-tenant-stage-permissions")
    Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(ViewTenantStagePermissionsRequest request);

    @POST
    @Path("/verify-tenant-stage-permission-exists")
    Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            VerifyTenantStagePermissionExistsRequest request);

    @POST
    @Path("/sync-tenant-stage-permission")
    Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(SyncTenantStagePermissionRequest request);

    @POST
    @Path("/delete-tenant-stage-permission")
    Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(DeleteTenantStagePermissionRequest request);

    /*
    TenantVersion
     */

    @POST
    @Path("/get-tenant-version")
    Uni<GetTenantVersionResponse> getTenantVersion(GetTenantVersionRequest request);

    @POST
    @Path("/get-tenant-version-config")
    Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(GetTenantVersionConfigRequest request);

    @POST
    @Path("/get-tenant-version-data")
    Uni<GetTenantVersionDataResponse> getTenantVersionData(GetTenantVersionDataRequest request);

    @POST
    @Path("/sync-tenant-version")
    Uni<SyncTenantVersionResponse> syncTenantVersion(SyncTenantVersionRequest request);

    @POST
    @Path("/view-tenant-versions")
    Uni<ViewTenantVersionsResponse> viewTenantVersions(ViewTenantVersionsRequest request);

    @POST
    @Path("/delete-tenant-version")
    Uni<DeleteTenantVersionResponse> deleteTenantVersion(DeleteTenantVersionRequest request);

    /*
    TenantFilesArchive
     */

    @POST
    @Path("/get-tenant-files-archive")
    Uni<GetTenantFilesArchiveResponse> getTenantFilesArchive(GetTenantFilesArchiveRequest request);

    @POST
    @Path("/find-tenant-files-archive")
    Uni<FindTenantFilesArchiveResponse> findTenantFilesArchive(FindTenantFilesArchiveRequest request);

    @POST
    @Path("/view-tenant-files-archives")
    Uni<ViewTenantFilesArchivesResponse> viewTenantFilesArchives(ViewTenantFilesArchivesRequest request);

    @POST
    @Path("/sync-tenant-files-archive")
    Uni<SyncTenantFilesArchiveResponse> syncTenantFilesArchive(SyncTenantFilesArchiveRequest request);

    @POST
    @Path("/delete-tenant-files-archive")
    Uni<DeleteTenantFilesArchiveResponse> deleteTenantFilesArchive(DeleteTenantFilesArchiveRequest request);

    /*
    TenantBuildRequest
     */

    @POST
    @Path("/get-tenant-build-request")
    Uni<GetTenantBuildRequestResponse> getTenantBuildRequest(GetTenantBuildRequestRequest request);

    @POST
    @Path("/view-tenant-build-requests")
    Uni<ViewTenantBuildRequestsResponse> viewTenantBuildRequests(ViewTenantBuildRequestsRequest request);

    @POST
    @Path("/sync-tenant-build-request")
    Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequest(SyncTenantBuildRequestRequest request);

    @POST
    @Path("/delete-tenant-build-request")
    Uni<DeleteTenantBuildRequestResponse> deleteTenantBuildRequest(DeleteTenantBuildRequestRequest request);

    /*
    TenantImage
     */

    @POST
    @Path("/get-tenant-image")
    Uni<GetTenantImageResponse> getTenantImage(GetTenantImageRequest request);

    @POST
    @Path("/find-tenant-image")
    Uni<FindTenantImageResponse> findTenantImage(FindTenantImageRequest request);

    @POST
    @Path("/view-tenant-images")
    Uni<ViewTenantImagesResponse> viewTenantImages(ViewTenantImagesRequest request);

    @POST
    @Path("/sync-tenant-image")
    Uni<SyncTenantImageResponse> syncTenantImage(SyncTenantImageRequest request);

    @POST
    @Path("/delete-tenant-image")
    Uni<DeleteTenantImageResponse> deleteTenantImage(DeleteTenantImageRequest request);

    /*
    TenantDeployment
     */

    @POST
    @Path("/get-tenant-deployment")
    Uni<GetTenantDeploymentResponse> getTenantDeployment(GetTenantDeploymentRequest request);

    @POST
    @Path("/get-tenant-deployment-data")
    Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(GetTenantDeploymentDataRequest request);

    @POST
    @Path("/select-tenant-deployment")
    Uni<SelectTenantDeploymentResponse> selectTenantDeployment(SelectTenantDeploymentRequest request);

    @POST
    @Path("/view-tenant-deployments")
    Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(ViewTenantDeploymentsRequest request);

    @POST
    @Path("/sync-tenant-deployment")
    Uni<SyncTenantDeploymentResponse> syncTenantDeployment(SyncTenantDeploymentRequest request);

    @POST
    @Path("/delete-tenant-deployment")
    Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(DeleteTenantDeploymentRequest request);

    /*
    TenantLobbyResource
     */

    @POST
    @Path("/get-tenant-lobby-resource")
    Uni<GetTenantLobbyResourceResponse> execute(GetTenantLobbyResourceRequest request);

    @POST
    @Path("/find-tenant-lobby-resource")
    Uni<FindTenantLobbyResourceResponse> execute(FindTenantLobbyResourceRequest request);

    @POST
    @Path("/view-tenant-lobby-resources")
    Uni<ViewTenantLobbyResourcesResponse> execute(ViewTenantLobbyResourcesRequest request);

    @POST
    @Path("/sync-tenant-lobby-resource")
    Uni<SyncTenantLobbyResourceResponse> execute(SyncTenantLobbyResourceRequest request);

    @POST
    @Path("/delete-tenant-lobby-resource")
    Uni<DeleteTenantLobbyResourceResponse> execute(DeleteTenantLobbyResourceRequest request);

    /*
    TenantLobbyRef
     */

    @POST
    @Path("/get-tenant-lobby-ref")
    Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(GetTenantLobbyRefRequest request);

    @POST
    @Path("/find-tenant-lobby-ref")
    Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(FindTenantLobbyRefRequest request);

    @POST
    @Path("/view-tenant-lobby-refs")
    Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(ViewTenantLobbyRefsRequest request);

    @POST
    @Path("/sync-tenant-lobby-ref")
    Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(SyncTenantLobbyRefRequest request);

    @POST
    @Path("/delete-tenant-lobby-ref")
    Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(DeleteTenantLobbyRefRequest request);

    /*
    TenantMatchmakerResource
     */

    @POST
    @Path("/get-tenant-matchmaker-resource")
    Uni<GetTenantMatchmakerResourceResponse> execute(GetTenantMatchmakerResourceRequest request);

    @POST
    @Path("/find-tenant-matchmaker-resource")
    Uni<FindTenantMatchmakerResourceResponse> execute(FindTenantMatchmakerResourceRequest request);

    @POST
    @Path("/view-tenant-matchmaker-resources")
    Uni<ViewTenantMatchmakerResourcesResponse> execute(
            ViewTenantMatchmakerResourcesRequest request);

    @POST
    @Path("/sync-tenant-matchmaker-resource")
    Uni<SyncTenantMatchmakerResourceResponse> execute(SyncTenantMatchmakerResourceRequest request);

    @POST
    @Path("/delete-tenant-matchmaker-resource")
    Uni<DeleteTenantMatchmakerResourceResponse> execute(DeleteTenantMatchmakerResourceRequest request);

    /*
    TenantMatchmakerRef
     */

    @POST
    @Path("/get-tenant-matchmaker-ref")
    Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(GetTenantMatchmakerRefRequest request);

    @POST
    @Path("/find-tenant-matchmaker-ref")
    Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(FindTenantMatchmakerRefRequest request);

    @POST
    @Path("/view-tenant-matchmaker-refs")
    Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(ViewTenantMatchmakerRefsRequest request);

    @POST
    @Path("/sync-version-matchmaker-ref")
    Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(SyncTenantMatchmakerRefRequest request);

    @POST
    @Path("/delete-tenant-matchmaker-ref")
    Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(DeleteTenantMatchmakerRefRequest request);
}
