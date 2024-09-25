package com.omgservers.service.module.tenant.impl.service.webService;

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
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsResponse;
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

public interface WebService {

    /*
    Tenant
     */

    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    Uni<GetTenantDataResponse> getTenantData(GetTenantDataRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    Uni<DeleteTenantResponse> deleteTenant(DeleteTenantRequest request);

    /*
    TenantPermission
     */

    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(ViewTenantPermissionsRequest request);

    Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(VerifyTenantPermissionExistsRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    Uni<GetTenantProjectResponse> getTenantProject(GetTenantProjectRequest request);

    Uni<GetTenantProjectDataResponse> getTenantProjectData(GetTenantProjectDataRequest request);

    Uni<SyncTenantProjectResponse> syncTenantProject(SyncTenantProjectRequest request);

    Uni<ViewTenantProjectsResponse> viewTenantProjects(ViewTenantProjectsRequest request);

    Uni<DeleteTenantProjectResponse> deleteTenantProject(DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(ViewTenantProjectPermissionsRequest request);

    Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            VerifyTenantProjectPermissionExistsRequest request);

    Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(SyncTenantProjectPermissionRequest request);

    Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    Uni<GetTenantStageResponse> getTenantStage(GetTenantStageRequest request);

    Uni<GetTenantStageDataResponse> getTenantStageData(GetTenantStageDataRequest request);

    Uni<SyncTenantStageResponse> syncTenantStage(SyncTenantStageRequest request);

    Uni<ViewTenantStagesResponse> viewTenantStages(ViewTenantStagesRequest request);

    Uni<DeleteTenantStageResponse> deleteTenantStage(DeleteTenantStageRequest request);

    /*
    TenantStagePermission
     */

    Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(ViewTenantStagePermissionsRequest request);

    Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            VerifyTenantStagePermissionExistsRequest request);

    Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(SyncTenantStagePermissionRequest request);

    Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(DeleteTenantStagePermissionRequest request);

    /*
    TenantVersion
     */

    Uni<GetTenantVersionResponse> getTenantVersion(GetTenantVersionRequest request);

    Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(GetTenantVersionConfigRequest request);

    Uni<GetTenantVersionDataResponse> getTenantVersionData(GetTenantVersionDataRequest request);

    Uni<SyncTenantVersionResponse> syncTenantVersion(SyncTenantVersionRequest request);

    Uni<ViewTenantVersionsResponse> viewTenantVersions(ViewTenantVersionsRequest request);

    Uni<DeleteTenantVersionResponse> deleteTenantVersion(DeleteTenantVersionRequest request);

    /*
    TenantJenkinsRequest
     */

    Uni<GetTenantJenkinsRequestResponse> getTenantJenkinsRequest(GetTenantJenkinsRequestRequest request);

    Uni<ViewTenantJenkinsRequestsResponse> viewTenantJenkinsRequests(ViewTenantJenkinsRequestsRequest request);

    Uni<SyncTenantJenkinsRequestResponse> syncTenantJenkinsRequest(SyncTenantJenkinsRequestRequest request);

    Uni<DeleteTenantJenkinsRequestResponse> deleteTenantJenkinsRequest(DeleteTenantJenkinsRequestRequest request);

    /*
    TenantImageRef
     */

    Uni<GetTenantImageRefResponse> getTenantImageRef(GetTenantImageRefRequest request);

    Uni<FindTenantImageRefResponse> findTenantImageRef(FindTenantImageRefRequest request);

    Uni<ViewTenantImageRefsResponse> viewTenantImageRefs(ViewTenantImageRefsRequest request);

    Uni<SyncTenantImageRefResponse> syncTenantImageRef(SyncTenantImageRefRequest request);

    Uni<DeleteTenantImageRefResponse> deleteTenantImageRef(DeleteTenantImageRefRequest request);

    /*
    TenantDeployment
     */

    Uni<GetTenantDeploymentResponse> getTenantDeployment(GetTenantDeploymentRequest request);

    Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(GetTenantDeploymentDataRequest request);

    Uni<SelectTenantDeploymentResponse> selectTenantDeployment(SelectTenantDeploymentRequest request);

    Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(ViewTenantDeploymentsRequest request);

    Uni<SyncTenantDeploymentResponse> syncTenantDeployment(SyncTenantDeploymentRequest request);

    Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(DeleteTenantDeploymentRequest request);

    /*
    TenantLobbyRequest
     */

    Uni<GetTenantLobbyRequestResponse> getTenantLobbyRequest(GetTenantLobbyRequestRequest request);

    Uni<FindTenantLobbyRequestResponse> findTenantLobbyRequest(FindTenantLobbyRequestRequest request);

    Uni<ViewTenantLobbyRequestsResponse> viewTenantLobbyRequests(ViewTenantLobbyRequestsRequest request);

    Uni<SyncTenantLobbyRequestResponse> syncTenantLobbyRequest(SyncTenantLobbyRequestRequest request);

    Uni<DeleteTenantLobbyRequestResponse> deleteTenantLobbyRequest(DeleteTenantLobbyRequestRequest request);

    /*
    TenantLobbyRef
     */

    Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(GetTenantLobbyRefRequest request);

    Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(FindTenantLobbyRefRequest request);

    Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(ViewTenantLobbyRefsRequest request);

    Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(SyncTenantLobbyRefRequest request);

    Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(DeleteTenantLobbyRefRequest request);

    /*
    TenantMatchmakerRequest
     */

    Uni<GetTenantMatchmakerRequestResponse> getTenantMatchmakerRequest(GetTenantMatchmakerRequestRequest request);

    Uni<FindTenantMatchmakerRequestResponse> findTenantMatchmakerRequest(FindTenantMatchmakerRequestRequest request);

    Uni<ViewTenantMatchmakerRequestsResponse> viewTenantMatchmakerRequests(
            ViewTenantMatchmakerRequestsRequest request);

    Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequest(SyncTenantMatchmakerRequestRequest request);

    Uni<DeleteTenantMatchmakerRequestResponse> deleteTenantMatchmakerRequest(
            DeleteTenantMatchmakerRequestRequest request);

    /*
    TenantMatchmakerRef
     */

    Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(GetTenantMatchmakerRefRequest request);

    Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(FindTenantMatchmakerRefRequest request);

    Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(ViewTenantMatchmakerRefsRequest request);

    Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(SyncTenantMatchmakerRefRequest request);

    Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(DeleteTenantMatchmakerRefRequest request);
}
