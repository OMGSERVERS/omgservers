package com.omgservers.service.module.tenant.impl.service.tenantService.testInterface;

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
import com.omgservers.service.module.tenant.impl.service.tenantService.TenantService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final TenantService tenantService;

    /*
    Tenant
     */

    public GetTenantResponse getTenant(final GetTenantRequest request) {
        return tenantService.getTenant(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantDataResponse getTenantData(final GetTenantDataRequest request) {
        return tenantService.getTenantData(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantResponse syncTenant(final SyncTenantRequest request) {
        return tenantService.syncTenant(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantResponse deleteTenant(final DeleteTenantRequest request) {
        return tenantService.deleteTenant(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantPermission
     */

    public ViewTenantPermissionsResponse viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        return tenantService.viewTenantPermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantPermissionExistsResponse verifyTenantPermissionExists(
            final VerifyTenantPermissionExistsRequest request) {
        return tenantService.verifyTenantPermissionExists(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantPermissionResponse syncTenantPermission(final SyncTenantPermissionRequest request) {
        return tenantService.syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantPermissionResponse deleteTenantPermission(final DeleteTenantPermissionRequest request) {
        return tenantService.deleteTenantPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantProject
     */

    public GetTenantProjectResponse getTenantProject(final GetTenantProjectRequest request) {
        return tenantService.getTenantProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantProjectDataResponse getTenantProjectData(final GetTenantProjectDataRequest request) {
        return tenantService.getTenantProjectData(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantProjectResponse syncTenantProject(final SyncTenantProjectRequest request) {
        return tenantService.syncTenantProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantProjectsResponse viewTenantProjects(final ViewTenantProjectsRequest request) {
        return tenantService.viewTenantProjects(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantProjectResponse deleteTenantProject(final DeleteTenantProjectRequest request) {
        return tenantService.deleteTenantProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantProjectPermission
     */

    public ViewTenantProjectPermissionsResponse viewTenantProjectPermissions(
            final ViewTenantProjectPermissionsRequest request) {
        return tenantService.viewTenantProjectPermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantProjectPermissionExistsResponse verifyTenantProjectPermissionExists(
            final VerifyTenantProjectPermissionExistsRequest request) {
        return tenantService.verifyTenantProjectPermissionExists(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantProjectPermissionResponse syncTenantProjectPermission(
            final SyncTenantProjectPermissionRequest request) {
        return tenantService.syncTenantProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantProjectPermissionResponse deleteTenantProjectPermission(
            final DeleteTenantProjectPermissionRequest request) {
        return tenantService.deleteTenantProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantStage
     */

    public GetTenantStageResponse getTenantStage(final GetTenantStageRequest request) {
        return tenantService.getTenantStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantStageDataResponse getTenantStageData(final GetTenantStageDataRequest request) {
        return tenantService.getTenantStageData(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStageResponse syncTenantStage(final SyncTenantStageRequest request) {
        return tenantService.syncTenantStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantStagesResponse viewTenantStages(final ViewTenantStagesRequest request) {
        return tenantService.viewTenantStages(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantStageResponse deleteTenantStage(final DeleteTenantStageRequest request) {
        return tenantService.deleteTenantStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantStagePermission
     */

    public ViewTenantStagePermissionsResponse viewTenantStagePermissions(
            final ViewTenantStagePermissionsRequest request) {
        return tenantService.viewTenantStagePermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantStagePermissionExistsResponse verifyTenantStagePermissionExists(
            final VerifyTenantStagePermissionExistsRequest request) {
        return tenantService.verifyTenantStagePermissionExists(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStagePermissionResponse syncTenantStagePermission(final SyncTenantStagePermissionRequest request) {
        return tenantService.syncTenantStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStagePermissionResponse syncTenantStagePermissionWithIdempotency(
            final SyncTenantStagePermissionRequest request) {
        return tenantService.syncTenantStagePermissionWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantStagePermissionResponse deleteTenantStagePermission(
            final DeleteTenantStagePermissionRequest request) {
        return tenantService.deleteTenantStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantVersion
     */

    public GetTenantVersionResponse getTenantVersion(final GetTenantVersionRequest request) {
        return tenantService.getTenantVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantVersionConfigResponse getTenantVersionConfig(final GetTenantVersionConfigRequest request) {
        return tenantService.getTenantVersionConfig(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantVersionDataResponse getTenantVersionData(final GetTenantVersionDataRequest request) {
        return tenantService.getTenantVersionData(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantVersionResponse syncTenantVersion(final SyncTenantVersionRequest request) {
        return tenantService.syncTenantVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantVersionsResponse viewTenantVersions(final ViewTenantVersionsRequest request) {
        return tenantService.viewTenantVersions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantVersionResponse deleteTenantVersion(final DeleteTenantVersionRequest request) {
        return tenantService.deleteTenantVersion(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantJenkinsRequest
     */

    public GetTenantJenkinsRequestResponse getTenantJenkinsRequest(final GetTenantJenkinsRequestRequest request) {
        return tenantService.getTenantJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantJenkinsRequestsResponse viewTenantJenkinsRequests(
            final ViewTenantJenkinsRequestsRequest request) {
        return tenantService.viewTenantJenkinsRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantJenkinsRequestResponse syncTenantJenkinsRequest(final SyncTenantJenkinsRequestRequest request) {
        return tenantService.syncTenantJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantJenkinsRequestResponse syncTenantJenkinsRequestWithIdempotency(
            final SyncTenantJenkinsRequestRequest request) {
        return tenantService.syncTenantJenkinsRequestWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantJenkinsRequestResponse deleteTenantJenkinsRequest(
            final DeleteTenantJenkinsRequestRequest request) {
        return tenantService.deleteTenantJenkinsRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantImage
     */

    public GetTenantImageResponse getTenantImage(final GetTenantImageRequest request) {
        return tenantService.getTenantImage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantImageResponse findTenantImage(final FindTenantImageRequest request) {
        return tenantService.findTenantImage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantImageResponse viewTenantImages(final ViewTenantImageRequest request) {
        return tenantService.viewTenantImages(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantImageResponse syncTenantImage(final SyncTenantImageRequest request) {
        return tenantService.syncTenantImage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantImageResponse syncTenantImageWithIdempotency(final SyncTenantImageRequest request) {
        return tenantService.syncTenantImageWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantImageResponse deleteTenantImage(final DeleteTenantImageRequest request) {
        return tenantService.deleteTenantImage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantDeployment
     */

    public GetTenantDeploymentResponse getTenantDeployment(GetTenantDeploymentRequest request) {
        return tenantService.getTenantDeployment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SelectTenantDeploymentResponse selectTenantDeployment(SelectTenantDeploymentRequest request) {
        return tenantService.selectTenantDeployment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantDeploymentsResponse viewTenantDeployments(ViewTenantDeploymentsRequest request) {
        return tenantService.viewTenantDeployments(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantDeploymentResponse syncTenantDeployment(SyncTenantDeploymentRequest request) {
        return tenantService.syncTenantDeployment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantDeploymentResponse deleteTenantDeployment(DeleteTenantDeploymentRequest request) {
        return tenantService.deleteTenantDeployment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantLobbyRequest
     */

    public GetTenantLobbyRequestResponse getTenantLobbyRequest(final GetTenantLobbyRequestRequest request) {
        return tenantService.getTenantLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantLobbyRequestResponse findTenantLobbyRequest(final FindTenantLobbyRequestRequest request) {
        return tenantService.findTenantLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantLobbyRequestsResponse viewTenantLobbyRequests(final ViewTenantLobbyRequestsRequest request) {
        return tenantService.viewTenantLobbyRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyRequestResponse syncTenantLobbyRequest(final SyncTenantLobbyRequestRequest request) {
        return tenantService.syncTenantLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyRequestResponse syncTenantLobbyRequestWithIdempotency(
            final SyncTenantLobbyRequestRequest request) {
        return tenantService.syncTenantLobbyRequestWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantLobbyRequestResponse deleteTenantLobbyRequest(final DeleteTenantLobbyRequestRequest request) {
        return tenantService.deleteTenantLobbyRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantLobbyRef
     */

    public GetTenantLobbyRefResponse getTenantLobbyRef(final GetTenantLobbyRefRequest request) {
        return tenantService.getTenantLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantLobbyRefResponse findTenantLobbyRef(final FindTenantLobbyRefRequest request) {
        return tenantService.findTenantLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantLobbyRefsResponse viewTenantLobbyRefs(final ViewTenantLobbyRefsRequest request) {
        return tenantService.viewTenantLobbyRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyRefResponse syncTenantLobbyRef(final SyncTenantLobbyRefRequest request) {
        return tenantService.syncTenantLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyRefResponse syncTenantLobbyRefWithIdempotency(final SyncTenantLobbyRefRequest request) {
        return tenantService.syncTenantLobbyRefWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantLobbyRefResponse deleteTenantLobbyRef(final DeleteTenantLobbyRefRequest request) {
        return tenantService.deleteTenantLobbyRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantMatchmakerRequest
     */

    public GetTenantMatchmakerRequestResponse getTenantMatchmakerRequest(
            final GetTenantMatchmakerRequestRequest request) {
        return tenantService.getTenantMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantMatchmakerRequestResponse findTenantMatchmakerRequest(
            final FindTenantMatchmakerRequestRequest request) {
        return tenantService.findTenantMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantMatchmakerRequestsResponse viewTenantMatchmakerRequests(
            final ViewTenantMatchmakerRequestsRequest request) {
        return tenantService.viewTenantMatchmakerRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerRequestResponse syncTenantMatchmakerRequest(
            final SyncTenantMatchmakerRequestRequest request) {
        return tenantService.syncTenantMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerRequestResponse syncTenantMatchmakerRequestWithIdempotency(
            final SyncTenantMatchmakerRequestRequest request) {
        return tenantService.syncTenantMatchmakerRequestWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantMatchmakerRequestResponse deleteTenantMatchmakerRequest(
            final DeleteTenantMatchmakerRequestRequest request) {
        return tenantService.deleteTenantMatchmakerRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantMatchmakerRef
     */

    public GetTenantMatchmakerRefResponse getTenantMatchmakerRef(final GetTenantMatchmakerRefRequest request) {
        return tenantService.getTenantMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantMatchmakerRefResponse findTenantMatchmakerRef(final FindTenantMatchmakerRefRequest request) {
        return tenantService.findTenantMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantMatchmakerRefsResponse viewTenantMatchmakerRefs(final ViewTenantMatchmakerRefsRequest request) {
        return tenantService.viewTenantMatchmakerRefs(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerRefResponse syncTenantMatchmakerRef(final SyncTenantMatchmakerRefRequest request) {
        return tenantService.syncTenantMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerRefResponse syncTenantMatchmakerRefWithIdempotency(
            final SyncTenantMatchmakerRefRequest request) {
        return tenantService.syncTenantMatchmakerRefWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantMatchmakerRefResponse deleteTenantMatchmakerRef(final DeleteTenantMatchmakerRefRequest request) {
        return tenantService.deleteTenantMatchmakerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
