package com.omgservers.service.module.tenant.impl.service.tenantService;

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
import jakarta.validation.Valid;

public interface TenantService {

    /*
    Tenant
     */

    Uni<GetTenantResponse> getTenant(@Valid GetTenantRequest request);

    Uni<GetTenantDataResponse> getTenantData(@Valid GetTenantDataRequest request);

    Uni<SyncTenantResponse> syncTenant(@Valid SyncTenantRequest request);

    Uni<DeleteTenantResponse> deleteTenant(@Valid DeleteTenantRequest request);

    /*
    TenantPermission
     */

    Uni<ViewTenantPermissionsResponse> viewTenantPermissions(@Valid ViewTenantPermissionsRequest request);

    Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(
            @Valid VerifyTenantPermissionExistsRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(@Valid SyncTenantPermissionRequest request);

    Uni<DeleteTenantPermissionResponse> deleteTenantPermission(@Valid DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    Uni<GetTenantProjectResponse> getTenantProject(@Valid GetTenantProjectRequest request);

    Uni<GetTenantProjectDataResponse> getTenantProjectData(@Valid GetTenantProjectDataRequest request);

    Uni<SyncTenantProjectResponse> syncTenantProject(@Valid SyncTenantProjectRequest request);

    Uni<ViewTenantProjectsResponse> viewTenantProjects(@Valid ViewTenantProjectsRequest request);

    Uni<DeleteTenantProjectResponse> deleteTenantProject(@Valid DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(
            @Valid ViewTenantProjectPermissionsRequest request);

    Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            @Valid VerifyTenantProjectPermissionExistsRequest request);

    Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(
            @Valid SyncTenantProjectPermissionRequest request);

    Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermissionWithIdempotency(
            @Valid SyncTenantProjectPermissionRequest request);

    Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            @Valid DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    Uni<GetTenantStageResponse> getTenantStage(@Valid GetTenantStageRequest request);

    Uni<GetTenantStageDataResponse> getTenantStageData(@Valid GetTenantStageDataRequest request);

    Uni<SyncTenantStageResponse> syncTenantStage(@Valid SyncTenantStageRequest request);

    Uni<ViewTenantStagesResponse> viewTenantStages(@Valid ViewTenantStagesRequest request);

    Uni<DeleteTenantStageResponse> deleteTenantStage(@Valid DeleteTenantStageRequest request);

    /*
    TenantStagePermission
     */

    Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(
            @Valid ViewTenantStagePermissionsRequest request);

    Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            @Valid VerifyTenantStagePermissionExistsRequest request);

    Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(@Valid SyncTenantStagePermissionRequest request);

    Uni<SyncTenantStagePermissionResponse> syncTenantStagePermissionWithIdempotency(
            @Valid SyncTenantStagePermissionRequest request);

    Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(
            @Valid DeleteTenantStagePermissionRequest request);

    /*
    TenantVersion
     */

    Uni<GetTenantVersionResponse> getTenantVersion(@Valid GetTenantVersionRequest request);

    Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(@Valid GetTenantVersionConfigRequest request);

    Uni<GetTenantVersionDataResponse> getTenantVersionData(@Valid GetTenantVersionDataRequest request);

    Uni<SyncTenantVersionResponse> syncTenantVersion(@Valid SyncTenantVersionRequest request);

    Uni<ViewTenantVersionsResponse> viewTenantVersions(@Valid ViewTenantVersionsRequest request);

    Uni<DeleteTenantVersionResponse> deleteTenantVersion(@Valid DeleteTenantVersionRequest request);

    /*
    TenantJenkinsRequest
     */

    Uni<GetTenantJenkinsRequestResponse> getTenantJenkinsRequest(@Valid GetTenantJenkinsRequestRequest request);

    Uni<ViewTenantJenkinsRequestsResponse> viewTenantJenkinsRequests(
            @Valid ViewTenantJenkinsRequestsRequest request);

    Uni<SyncTenantJenkinsRequestResponse> syncTenantJenkinsRequest(@Valid SyncTenantJenkinsRequestRequest request);

    Uni<SyncTenantJenkinsRequestResponse> syncTenantJenkinsRequestWithIdempotency(
            @Valid SyncTenantJenkinsRequestRequest request);

    Uni<DeleteTenantJenkinsRequestResponse> deleteTenantJenkinsRequest(
            @Valid DeleteTenantJenkinsRequestRequest request);

    /*
    TenantImage
     */

    Uni<GetTenantImageResponse> getTenantImage(@Valid GetTenantImageRequest request);

    Uni<FindTenantImageResponse> findTenantImage(@Valid FindTenantImageRequest request);

    Uni<ViewTenantImageResponse> viewTenantImages(@Valid ViewTenantImageRequest request);

    Uni<SyncTenantImageResponse> syncTenantImage(@Valid SyncTenantImageRequest request);

    Uni<SyncTenantImageResponse> syncTenantImageWithIdempotency(@Valid SyncTenantImageRequest request);

    Uni<DeleteTenantImageResponse> deleteTenantImage(@Valid DeleteTenantImageRequest request);

    /*
    TenantDeployment
     */

    Uni<GetTenantDeploymentResponse> getTenantDeployment(@Valid GetTenantDeploymentRequest request);

    Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(@Valid GetTenantDeploymentDataRequest request);

    Uni<SelectTenantDeploymentResponse> selectTenantDeployment(@Valid SelectTenantDeploymentRequest request);

    Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(@Valid ViewTenantDeploymentsRequest request);

    Uni<SyncTenantDeploymentResponse> syncTenantDeployment(@Valid SyncTenantDeploymentRequest request);

    Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(@Valid DeleteTenantDeploymentRequest request);

    /*
    TenantLobbyRequest
     */

    Uni<GetTenantLobbyRequestResponse> getTenantLobbyRequest(@Valid GetTenantLobbyRequestRequest request);

    Uni<FindTenantLobbyRequestResponse> findTenantLobbyRequest(@Valid FindTenantLobbyRequestRequest request);

    Uni<ViewTenantLobbyRequestsResponse> viewTenantLobbyRequests(@Valid ViewTenantLobbyRequestsRequest request);

    Uni<SyncTenantLobbyRequestResponse> syncTenantLobbyRequest(@Valid SyncTenantLobbyRequestRequest request);

    Uni<SyncTenantLobbyRequestResponse> syncTenantLobbyRequestWithIdempotency(
            @Valid SyncTenantLobbyRequestRequest request);

    Uni<DeleteTenantLobbyRequestResponse> deleteTenantLobbyRequest(@Valid DeleteTenantLobbyRequestRequest request);

    /*
    TenantLobbyRef
     */

    Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(@Valid GetTenantLobbyRefRequest request);

    Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(@Valid FindTenantLobbyRefRequest request);

    Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(@Valid ViewTenantLobbyRefsRequest request);

    Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(@Valid SyncTenantLobbyRefRequest request);

    Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRefWithIdempotency(@Valid SyncTenantLobbyRefRequest request);

    Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(@Valid DeleteTenantLobbyRefRequest request);

    /*
    TenantMatchmakerRequest
     */

    Uni<GetTenantMatchmakerRequestResponse> getTenantMatchmakerRequest(
            @Valid GetTenantMatchmakerRequestRequest request);

    Uni<FindTenantMatchmakerRequestResponse> findTenantMatchmakerRequest(
            @Valid FindTenantMatchmakerRequestRequest request);

    Uni<ViewTenantMatchmakerRequestsResponse> viewTenantMatchmakerRequests(
            @Valid ViewTenantMatchmakerRequestsRequest request);

    Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequest(
            @Valid SyncTenantMatchmakerRequestRequest request);

    Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequestWithIdempotency(
            @Valid SyncTenantMatchmakerRequestRequest request);

    Uni<DeleteTenantMatchmakerRequestResponse> deleteTenantMatchmakerRequest(
            @Valid DeleteTenantMatchmakerRequestRequest request);

    /*
    TenantMatchmakerRef
     */

    Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(@Valid GetTenantMatchmakerRefRequest request);

    Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(@Valid FindTenantMatchmakerRefRequest request);

    Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(@Valid ViewTenantMatchmakerRefsRequest request);

    Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(@Valid SyncTenantMatchmakerRefRequest request);

    Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRefWithIdempotency(
            @Valid SyncTenantMatchmakerRefRequest request);

    Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(
            @Valid DeleteTenantMatchmakerRefRequest request);
}
