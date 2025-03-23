package com.omgservers.service.shard.tenant.impl.service.tenantService.testInterface;

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
import com.omgservers.service.shard.tenant.impl.service.tenantService.TenantService;
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
    TenantFilesArchive
     */

    public GetTenantFilesArchiveResponse getTenantFilesArchive(final GetTenantFilesArchiveRequest request) {
        return tenantService.getTenantFilesArchive(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantFilesArchiveResponse findTenantFilesArchive(final FindTenantFilesArchiveRequest request) {
        return tenantService.findTenantFilesArchive(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantFilesArchivesResponse viewTenantFilesArchives(final ViewTenantFilesArchivesRequest request) {
        return tenantService.viewTenantFilesArchives(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantFilesArchiveResponse syncTenantFilesArchive(final SyncTenantFilesArchiveRequest request) {
        return tenantService.syncTenantFilesArchive(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantFilesArchiveResponse deleteTenantFilesArchive(final DeleteTenantFilesArchiveRequest request) {
        return tenantService.deleteTenantFilesArchive(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantBuildRequest
     */

    public GetTenantBuildRequestResponse getTenantBuildRequest(final GetTenantBuildRequestRequest request) {
        return tenantService.getTenantBuildRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantBuildRequestsResponse viewTenantBuildRequests(
            final ViewTenantBuildRequestsRequest request) {
        return tenantService.viewTenantBuildRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantBuildRequestResponse syncTenantBuildRequest(final SyncTenantBuildRequestRequest request) {
        return tenantService.syncTenantBuildRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantBuildRequestResponse syncTenantBuildRequestWithIdempotency(
            final SyncTenantBuildRequestRequest request) {
        return tenantService.syncTenantBuildRequestWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantBuildRequestResponse deleteTenantBuildRequest(
            final DeleteTenantBuildRequestRequest request) {
        return tenantService.deleteTenantBuildRequest(request)
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

    public ViewTenantImagesResponse viewTenantImages(final ViewTenantImagesRequest request) {
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
    TenantLobbyResource
     */

    public GetTenantLobbyResourceResponse execute(final GetTenantLobbyResourceRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantLobbyResourceResponse execute(final FindTenantLobbyResourceRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantLobbyResourcesResponse execute(final ViewTenantLobbyResourcesRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyResourceResponse execute(final SyncTenantLobbyResourceRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantLobbyResourceResponse executeWithIdempotency(final SyncTenantLobbyResourceRequest request) {
        return tenantService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantLobbyResourceResponse execute(final DeleteTenantLobbyResourceRequest request) {
        return tenantService.execute(request)
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
    TenantMatchmakerResource
     */

    public GetTenantMatchmakerResourceResponse execute(final GetTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantMatchmakerResourceResponse execute(final FindTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantMatchmakerResourcesResponse execute(final ViewTenantMatchmakerResourcesRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerResourceResponse execute(final SyncTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantMatchmakerResourceResponse executeWithIdempotency(final SyncTenantMatchmakerResourceRequest request) {
        return tenantService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantMatchmakerResourceResponse execute(final DeleteTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request)
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
