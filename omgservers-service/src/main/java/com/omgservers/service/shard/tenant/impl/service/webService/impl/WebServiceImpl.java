package com.omgservers.service.shard.tenant.impl.service.webService.impl;

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
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        return tenantService.getTenant(request);
    }

    @Override
    public Uni<GetTenantDataResponse> getTenantData(final GetTenantDataRequest request) {
        return tenantService.getTenantData(request);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(final SyncTenantRequest request) {
        return tenantService.syncTenant(request);
    }

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(final DeleteTenantRequest request) {
        return tenantService.deleteTenant(request);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        return tenantService.viewTenantPermissions(request);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(
            final VerifyTenantPermissionExistsRequest request) {
        return tenantService.verifyTenantPermissionExists(request);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        return tenantService.syncTenantPermission(request);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> deleteTenantPermission(final DeleteTenantPermissionRequest request) {
        return tenantService.deleteTenantPermission(request);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> getTenantProject(final GetTenantProjectRequest request) {
        return tenantService.getTenantProject(request);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> getTenantProjectData(final GetTenantProjectDataRequest request) {
        return tenantService.getTenantProjectData(request);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> viewTenantProjects(final ViewTenantProjectsRequest request) {
        return tenantService.viewTenantProjects(request);
    }

    @Override
    public Uni<SyncTenantProjectResponse> syncTenantProject(final SyncTenantProjectRequest request) {
        return tenantService.syncTenantProject(request);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> deleteTenantProject(final DeleteTenantProjectRequest request) {
        return tenantService.deleteTenantProject(request);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(
            final ViewTenantProjectPermissionsRequest request) {
        return tenantService.viewTenantProjectPermissions(request);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            final VerifyTenantProjectPermissionExistsRequest request) {
        return tenantService.verifyTenantProjectPermissionExists(request);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(
            final SyncTenantProjectPermissionRequest request) {
        return tenantService.syncTenantProjectPermission(request);
    }

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            final DeleteTenantProjectPermissionRequest request) {
        return tenantService.deleteTenantProjectPermission(request);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> getTenantStage(final GetTenantStageRequest request) {
        return tenantService.getTenantStage(request);
    }

    @Override
    public Uni<GetTenantStageDataResponse> getTenantStageData(final GetTenantStageDataRequest request) {
        return tenantService.getTenantStageData(request);
    }

    @Override
    public Uni<ViewTenantStagesResponse> viewTenantStages(final ViewTenantStagesRequest request) {
        return tenantService.viewTenantStages(request);
    }

    @Override
    public Uni<SyncTenantStageResponse> syncTenantStage(final SyncTenantStageRequest request) {
        return tenantService.syncTenantStage(request);
    }

    @Override
    public Uni<DeleteTenantStageResponse> deleteTenantStage(final DeleteTenantStageRequest request) {
        return tenantService.deleteTenantStage(request);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(
            final ViewTenantStagePermissionsRequest request) {
        return tenantService.viewTenantStagePermissions(request);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            final VerifyTenantStagePermissionExistsRequest request) {
        return tenantService.verifyTenantStagePermissionExists(request);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(
            final SyncTenantStagePermissionRequest request) {
        return tenantService.syncTenantStagePermission(request);
    }

    @Override
    public Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(
            final DeleteTenantStagePermissionRequest request) {
        return tenantService.deleteTenantStagePermission(request);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> getTenantVersion(final GetTenantVersionRequest request) {
        return tenantService.getTenantVersion(request);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(final GetTenantVersionConfigRequest request) {
        return tenantService.getTenantVersionConfig(request);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> getTenantVersionData(final GetTenantVersionDataRequest request) {
        return tenantService.getTenantVersionData(request);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> viewTenantVersions(final ViewTenantVersionsRequest request) {
        return tenantService.viewTenantVersions(request);
    }

    @Override
    public Uni<SyncTenantVersionResponse> syncTenantVersion(final SyncTenantVersionRequest request) {
        return tenantService.syncTenantVersion(request);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> deleteTenantVersion(final DeleteTenantVersionRequest request) {
        return tenantService.deleteTenantVersion(request);
    }

    /*
    TenantFilesArchive
     */

    @Override
    public Uni<GetTenantFilesArchiveResponse> getTenantFilesArchive(final GetTenantFilesArchiveRequest request) {
        return tenantService.getTenantFilesArchive(request);
    }

    @Override
    public Uni<FindTenantFilesArchiveResponse> findTenantFilesArchive(final FindTenantFilesArchiveRequest request) {
        return tenantService.findTenantFilesArchive(request);
    }

    @Override
    public Uni<ViewTenantFilesArchivesResponse> viewTenantFilesArchives(final ViewTenantFilesArchivesRequest request) {
        return tenantService.viewTenantFilesArchives(request);
    }

    @Override
    public Uni<SyncTenantFilesArchiveResponse> syncTenantFilesArchive(final SyncTenantFilesArchiveRequest request) {
        return tenantService.syncTenantFilesArchive(request);
    }

    @Override
    public Uni<DeleteTenantFilesArchiveResponse> deleteTenantFilesArchive(
            final DeleteTenantFilesArchiveRequest request) {
        return tenantService.deleteTenantFilesArchive(request);
    }

    /*
    TenantBuildRequest
     */

    @Override
    public Uni<GetTenantBuildRequestResponse> getTenantBuildRequest(
            final GetTenantBuildRequestRequest request) {
        return tenantService.getTenantBuildRequest(request);
    }

    @Override
    public Uni<ViewTenantBuildRequestsResponse> viewTenantBuildRequests(
            final ViewTenantBuildRequestsRequest request) {
        return tenantService.viewTenantBuildRequests(request);
    }

    @Override
    public Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequest(
            final SyncTenantBuildRequestRequest request) {
        return tenantService.syncTenantBuildRequest(request);
    }

    @Override
    public Uni<DeleteTenantBuildRequestResponse> deleteTenantBuildRequest(
            final DeleteTenantBuildRequestRequest request) {
        return tenantService.deleteTenantBuildRequest(request);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> getTenantImage(final GetTenantImageRequest request) {
        return tenantService.getTenantImage(request);
    }

    @Override
    public Uni<FindTenantImageResponse> findTenantImage(final FindTenantImageRequest request) {
        return tenantService.findTenantImage(request);
    }

    @Override
    public Uni<ViewTenantImagesResponse> viewTenantImages(final ViewTenantImagesRequest request) {
        return tenantService.viewTenantImages(request);
    }

    @Override
    public Uni<SyncTenantImageResponse> syncTenantImage(final SyncTenantImageRequest request) {
        return tenantService.syncTenantImage(request);
    }

    @Override
    public Uni<DeleteTenantImageResponse> deleteTenantImage(final DeleteTenantImageRequest request) {
        return tenantService.deleteTenantImage(request);
    }

    /*
    TenantDeployment
     */

    @Override
    public Uni<GetTenantDeploymentResponse> getTenantDeployment(final GetTenantDeploymentRequest request) {
        return tenantService.getTenantDeployment(request);
    }

    @Override
    public Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(final GetTenantDeploymentDataRequest request) {
        return tenantService.getTenantDeploymentData(request);
    }

    @Override
    public Uni<SelectTenantDeploymentResponse> selectTenantDeployment(final SelectTenantDeploymentRequest request) {
        return tenantService.selectTenantDeployment(request);
    }

    @Override
    public Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(final ViewTenantDeploymentsRequest request) {
        return tenantService.viewTenantDeployments(request);
    }

    @Override
    public Uni<SyncTenantDeploymentResponse> syncTenantDeployment(final SyncTenantDeploymentRequest request) {
        return tenantService.syncTenantDeployment(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(final DeleteTenantDeploymentRequest request) {
        return tenantService.deleteTenantDeployment(request);
    }

    /*
    TenantLobbyResource
     */

    @Override
    public Uni<GetTenantLobbyResourceResponse> execute(final GetTenantLobbyResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<FindTenantLobbyResourceResponse> execute(final FindTenantLobbyResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantLobbyResourcesResponse> execute(final ViewTenantLobbyResourcesRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantLobbyResourceResponse> execute(final SyncTenantLobbyResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<UpdateTenantLobbyResourceStatusResponse> execute(final UpdateTenantLobbyResourceStatusRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantLobbyResourceResponse> execute(final DeleteTenantLobbyResourceRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantLobbyRef
     */

    @Override
    public Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(final GetTenantLobbyRefRequest request) {
        return tenantService.getTenantLobbyRef(request);
    }

    @Override
    public Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(final FindTenantLobbyRefRequest request) {
        return tenantService.findTenantLobbyRef(request);
    }

    @Override
    public Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(final ViewTenantLobbyRefsRequest request) {
        return tenantService.viewTenantLobbyRefs(request);
    }

    @Override
    public Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(final SyncTenantLobbyRefRequest request) {
        return tenantService.syncTenantLobbyRef(request);
    }

    @Override
    public Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(final DeleteTenantLobbyRefRequest request) {
        return tenantService.deleteTenantLobbyRef(request);
    }

    /*
    TenantMatchmakerResource
     */

    @Override
    public Uni<GetTenantMatchmakerResourceResponse> execute(final GetTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<FindTenantMatchmakerResourceResponse> execute(final FindTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<ViewTenantMatchmakerResourcesResponse> execute(final ViewTenantMatchmakerResourcesRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<SyncTenantMatchmakerResourceResponse> execute(final SyncTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request);
    }

    @Override
    public Uni<DeleteTenantMatchmakerResourceResponse> execute(
            final DeleteTenantMatchmakerResourceRequest request) {
        return tenantService.execute(request);
    }

    /*
    TenantMatchmakerRef
     */

    @Override
    public Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(final GetTenantMatchmakerRefRequest request) {
        return tenantService.getTenantMatchmakerRef(request);
    }

    @Override
    public Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(
            final SyncTenantMatchmakerRefRequest request) {
        return tenantService.syncTenantMatchmakerRef(request);
    }

    @Override
    public Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(
            final FindTenantMatchmakerRefRequest request) {
        return tenantService.findTenantMatchmakerRef(request);
    }

    @Override
    public Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(
            final ViewTenantMatchmakerRefsRequest request) {
        return tenantService.viewTenantMatchmakerRefs(request);
    }

    @Override
    public Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(
            final DeleteTenantMatchmakerRefRequest request) {
        return tenantService.deleteTenantMatchmakerRef(request);
    }
}
