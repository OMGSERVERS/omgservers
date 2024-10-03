package com.omgservers.service.module.tenant.impl.service.webService.impl.api;

import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenant.SyncTenantResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentDataResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsResponse;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.DeleteTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.SyncTenantLobbyRefResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.FindTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.SyncTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.DeleteTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionRequest;
import com.omgservers.schema.module.tenant.tenantPermission.SyncTenantPermissionResponse;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.VerifyTenantPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantPermission.ViewTenantPermissionsResponse;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionDataResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Tenant Module API")
@Path("/omgservers/v1/module/tenant/request")
public interface TenantApi {

    /*
    Tenant
     */

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    @PUT
    @Path("/get-tenant-data")
    Uni<GetTenantDataResponse> getTenantData(GetTenantDataRequest request);

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);

    /*
    TenantPermission
     */

    @PUT
    @Path("/view-tenant-permissions")
    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(ViewTenantPermissionsRequest request);

    @PUT
    @Path("/verify-tenant-permission-exist")
    Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(VerifyTenantPermissionExistsRequest request);

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    @PUT
    @Path("/delete-tenant-permission")
    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    @PUT
    @Path("/get-tenant-project")
    Uni<GetTenantProjectResponse> getTenantProject(GetTenantProjectRequest request);

    @PUT
    @Path("/get-tenant-project-data")
    Uni<GetTenantProjectDataResponse> getTenantProjectData(GetTenantProjectDataRequest request);

    @PUT
    @Path("/sync-tenant-project")
    Uni<SyncTenantProjectResponse> syncTenantProject(SyncTenantProjectRequest request);

    @PUT
    @Path("/view-tenant-projects")
    Uni<ViewTenantProjectsResponse> viewTenantProjects(ViewTenantProjectsRequest request);

    @PUT
    @Path("/delete-tenant-project")
    Uni<DeleteTenantProjectResponse> deleteTenantProject(DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    @PUT
    @Path("/view-tenant-project-permissions")
    Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(ViewTenantProjectPermissionsRequest request);

    @PUT
    @Path("/verify-tenant-project-permission-exists")
    Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            VerifyTenantProjectPermissionExistsRequest request);

    @PUT
    @Path("/sync-tenant-project-permission")
    Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(SyncTenantProjectPermissionRequest request);

    @PUT
    @Path("/delete-tenant-project-permission")
    Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    @PUT
    @Path("/get-tenant-stage")
    Uni<GetTenantStageResponse> getTenantStage(GetTenantStageRequest request);

    @PUT
    @Path("/get-tenant-stage-data")
    Uni<GetTenantStageDataResponse> getTenantStageData(GetTenantStageDataRequest request);

    @PUT
    @Path("/sync-tenant-stage")
    Uni<SyncTenantStageResponse> syncTenantStage(SyncTenantStageRequest request);

    @PUT
    @Path("/view-tenant-stages")
    Uni<ViewTenantStagesResponse> viewTenantStages(ViewTenantStagesRequest request);

    @PUT
    @Path("/delete-tenant-stage")
    Uni<DeleteTenantStageResponse> deleteTenantStage(DeleteTenantStageRequest request);

    /*
    TenantStagePermission
     */

    @PUT
    @Path("/view-tenant-stage-permissions")
    Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(ViewTenantStagePermissionsRequest request);

    @PUT
    @Path("/verify-tenant-stage-permission-exists")
    Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            VerifyTenantStagePermissionExistsRequest request);

    @PUT
    @Path("/sync-tenant-stage-permission")
    Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(SyncTenantStagePermissionRequest request);

    @PUT
    @Path("/delete-tenant-stage-permission")
    Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(DeleteTenantStagePermissionRequest request);

    /*
    TenantVersion
     */

    @PUT
    @Path("/get-tenant-version")
    Uni<GetTenantVersionResponse> getTenantVersion(GetTenantVersionRequest request);

    @PUT
    @Path("/get-tenant-version-config")
    Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(GetTenantVersionConfigRequest request);

    @PUT
    @Path("/get-tenant-version-data")
    Uni<GetTenantVersionDataResponse> getTenantVersionData(GetTenantVersionDataRequest request);

    @PUT
    @Path("/sync-tenant-version")
    Uni<SyncTenantVersionResponse> syncTenantVersion(SyncTenantVersionRequest request);

    @PUT
    @Path("/view-tenant-versions")
    Uni<ViewTenantVersionsResponse> viewTenantVersions(ViewTenantVersionsRequest request);

    @PUT
    @Path("/delete-tenant-version")
    Uni<DeleteTenantVersionResponse> deleteTenantVersion(DeleteTenantVersionRequest request);

    /*
    TenantJenkinsRequest
     */

    @PUT
    @Path("/get-tenant-jenkins-request")
    Uni<GetTenantJenkinsRequestResponse> getTenantJenkinsRequest(GetTenantJenkinsRequestRequest request);

    @PUT
    @Path("/view-tenant-jenkins-requests")
    Uni<ViewTenantJenkinsRequestsResponse> viewTenantJenkinsRequests(ViewTenantJenkinsRequestsRequest request);

    @PUT
    @Path("/sync-tenant-jenkins-request")
    Uni<SyncTenantJenkinsRequestResponse> syncTenantJenkinsRequest(SyncTenantJenkinsRequestRequest request);

    @PUT
    @Path("/delete-tenant-jenkins-request")
    Uni<DeleteTenantJenkinsRequestResponse> deleteTenantJenkinsRequest(DeleteTenantJenkinsRequestRequest request);

    /*
    TenantImage
     */

    @PUT
    @Path("/get-tenant-image")
    Uni<GetTenantImageResponse> getTenantImage(GetTenantImageRequest request);

    @PUT
    @Path("/find-tenant-image")
    Uni<FindTenantImageResponse> findTenantImage(FindTenantImageRequest request);

    @PUT
    @Path("/view-tenant-images")
    Uni<ViewTenantImageResponse> viewTenantImages(ViewTenantImageRequest request);

    @PUT
    @Path("/sync-tenant-image")
    Uni<SyncTenantImageResponse> syncTenantImage(SyncTenantImageRequest request);

    @PUT
    @Path("/delete-tenant-image")
    Uni<DeleteTenantImageResponse> deleteTenantImage(DeleteTenantImageRequest request);

    /*
    TenantDeployment
     */

    @PUT
    @Path("/get-tenant-deployment")
    Uni<GetTenantDeploymentResponse> getTenantDeployment(GetTenantDeploymentRequest request);

    @PUT
    @Path("/get-tenant-deployment-data")
    Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(GetTenantDeploymentDataRequest request);

    @PUT
    @Path("/select-tenant-deployment")
    Uni<SelectTenantDeploymentResponse> selectTenantDeployment(SelectTenantDeploymentRequest request);

    @PUT
    @Path("/view-tenant-deployments")
    Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(ViewTenantDeploymentsRequest request);

    @PUT
    @Path("/sync-tenant-deployment")
    Uni<SyncTenantDeploymentResponse> syncTenantDeployment(SyncTenantDeploymentRequest request);

    @PUT
    @Path("/delete-tenant-deployment")
    Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(DeleteTenantDeploymentRequest request);

    /*
    TenantLobbyRequest
     */

    @PUT
    @Path("/get-tenant-lobby-request")
    Uni<GetTenantLobbyRequestResponse> getTenantLobbyRequest(GetTenantLobbyRequestRequest request);

    @PUT
    @Path("/find-tenant-lobby-request")
    Uni<FindTenantLobbyRequestResponse> findTenantLobbyRequest(FindTenantLobbyRequestRequest request);

    @PUT
    @Path("/view-tenant-lobby-requests")
    Uni<ViewTenantLobbyRequestsResponse> viewTenantLobbyRequests(ViewTenantLobbyRequestsRequest request);

    @PUT
    @Path("/sync-tenant-lobby-request")
    Uni<SyncTenantLobbyRequestResponse> syncTenantLobbyRequest(SyncTenantLobbyRequestRequest request);

    @PUT
    @Path("/delete-tenant-lobby-request")
    Uni<DeleteTenantLobbyRequestResponse> deleteTenantLobbyRequest(DeleteTenantLobbyRequestRequest request);

    /*
    TenantLobbyRef
     */

    @PUT
    @Path("/get-tenant-lobby-ref")
    Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(GetTenantLobbyRefRequest request);

    @PUT
    @Path("/find-tenant-lobby-ref")
    Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(FindTenantLobbyRefRequest request);

    @PUT
    @Path("/view-tenant-lobby-refs")
    Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(ViewTenantLobbyRefsRequest request);

    @PUT
    @Path("/sync-tenant-lobby-ref")
    Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(SyncTenantLobbyRefRequest request);

    @PUT
    @Path("/delete-tenant-lobby-ref")
    Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(DeleteTenantLobbyRefRequest request);

    /*
    TenantMatchmakerRequest
     */

    @PUT
    @Path("/get-tenant-matchmaker-request")
    Uni<GetTenantMatchmakerRequestResponse> getTenantMatchmakerRequest(GetTenantMatchmakerRequestRequest request);

    @PUT
    @Path("/find-tenant-matchmaker-request")
    Uni<FindTenantMatchmakerRequestResponse> findTenantMatchmakerRequest(FindTenantMatchmakerRequestRequest request);

    @PUT
    @Path("/view-tenant-matchmaker-requests")
    Uni<ViewTenantMatchmakerRequestsResponse> viewTenantMatchmakerRequests(
            ViewTenantMatchmakerRequestsRequest request);

    @PUT
    @Path("/sync-tenant-matchmaker-request")
    Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequest(SyncTenantMatchmakerRequestRequest request);

    @PUT
    @Path("/delete-tenant-matchmaker-request")
    Uni<DeleteTenantMatchmakerRequestResponse> deleteTenantMatchmakerRequest(
            DeleteTenantMatchmakerRequestRequest request);

    /*
    TenantMatchmakerRef
     */

    @PUT
    @Path("/get-tenant-matchmaker-ref")
    Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(GetTenantMatchmakerRefRequest request);

    @PUT
    @Path("/find-tenant-matchmaker-ref")
    Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(FindTenantMatchmakerRefRequest request);

    @PUT
    @Path("/view-tenant-matchmaker-refs")
    Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(ViewTenantMatchmakerRefsRequest request);

    @PUT
    @Path("/sync-version-matchmaker-ref")
    Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(SyncTenantMatchmakerRefRequest request);

    @PUT
    @Path("/delete-tenant-matchmaker-ref")
    Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(DeleteTenantMatchmakerRefRequest request);
}
