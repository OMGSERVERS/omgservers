package com.omgservers.service.shard.tenant.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantRequest;
import com.omgservers.schema.module.tenant.tenant.DeleteTenantResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.module.tenant.tenant.SyncTenantRequest;
import com.omgservers.schema.module.tenant.tenant.SyncTenantResponse;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestResponse;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestResponse;
import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.SyncTenantBuildRequestResponse;
import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsResponse;
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
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.GetTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesResponse;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
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
import com.omgservers.schema.module.tenant.tenantLobbyResource.*;
import com.omgservers.schema.module.tenant.tenantLobbyResource.SyncTenantLobbyResourceResponse;
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
import com.omgservers.service.shard.tenant.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.SERVICE})
class TenantApiImpl implements TenantApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenant);
    }

    @Override
    public Uni<GetTenantDataResponse> getTenantData(final GetTenantDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantData);
    }

    @Override
    public Uni<SyncTenantResponse> syncTenant(final SyncTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenant);
    }

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(final DeleteTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    /*
    TenantPermission
     */

    @Override
    public Uni<ViewTenantPermissionsResponse> viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantPermissions);
    }

    @Override
    public Uni<VerifyTenantPermissionExistsResponse> verifyTenantPermissionExists(
            final VerifyTenantPermissionExistsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::verifyTenantPermissionExists);
    }

    @Override
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantPermission);
    }

    @Override
    public Uni<DeleteTenantPermissionResponse> deleteTenantPermission(final DeleteTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantPermission);
    }

    /*
    TenantProject
     */

    @Override
    public Uni<GetTenantProjectResponse> getTenantProject(final GetTenantProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantProject);
    }

    @Override
    public Uni<GetTenantProjectDataResponse> getTenantProjectData(final GetTenantProjectDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantProjectData);
    }

    @Override
    public Uni<SyncTenantProjectResponse> syncTenantProject(final SyncTenantProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantProject);
    }

    @Override
    public Uni<ViewTenantProjectsResponse> viewTenantProjects(ViewTenantProjectsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantProjects);
    }

    @Override
    public Uni<DeleteTenantProjectResponse> deleteTenantProject(final DeleteTenantProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantProject);
    }

    /*
    TenantProjectPermission
     */

    @Override
    public Uni<ViewTenantProjectPermissionsResponse> viewTenantProjectPermissions(
            final ViewTenantProjectPermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantProjectPermissions);
    }

    @Override
    public Uni<VerifyTenantProjectPermissionExistsResponse> verifyTenantProjectPermissionExists(
            final VerifyTenantProjectPermissionExistsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::verifyTenantProjectPermissionExists);
    }

    @Override
    public Uni<SyncTenantProjectPermissionResponse> syncTenantProjectPermission(
            final SyncTenantProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantProjectPermission);
    }

    @Override
    public Uni<DeleteTenantProjectPermissionResponse> deleteTenantProjectPermission(
            final DeleteTenantProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantProjectPermission);
    }

    /*
    TenantStage
     */

    @Override
    public Uni<GetTenantStageResponse> getTenantStage(final GetTenantStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantStage);
    }

    @Override
    public Uni<GetTenantStageDataResponse> getTenantStageData(final GetTenantStageDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantStageData);
    }

    @Override
    public Uni<SyncTenantStageResponse> syncTenantStage(final SyncTenantStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantStage);
    }

    @Override
    public Uni<ViewTenantStagesResponse> viewTenantStages(ViewTenantStagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantStages);
    }

    @Override
    public Uni<DeleteTenantStageResponse> deleteTenantStage(final DeleteTenantStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantStage);
    }

    /*
    TenantStagePermission
     */

    @Override
    public Uni<ViewTenantStagePermissionsResponse> viewTenantStagePermissions(
            final ViewTenantStagePermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantStagePermissions);
    }

    @Override
    public Uni<VerifyTenantStagePermissionExistsResponse> verifyTenantStagePermissionExists(
            final VerifyTenantStagePermissionExistsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::verifyTenantStagePermissionExists);
    }

    @Override
    public Uni<SyncTenantStagePermissionResponse> syncTenantStagePermission(
            final SyncTenantStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantStagePermission);
    }

    @Override
    public Uni<DeleteTenantStagePermissionResponse> deleteTenantStagePermission(
            final DeleteTenantStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantStagePermission);
    }

    /*
    TenantVersion
     */

    @Override
    public Uni<GetTenantVersionResponse> getTenantVersion(final GetTenantVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantVersion);
    }

    @Override
    public Uni<GetTenantVersionConfigResponse> getTenantVersionConfig(final GetTenantVersionConfigRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantVersionConfig);
    }

    @Override
    public Uni<GetTenantVersionDataResponse> getTenantVersionData(final GetTenantVersionDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantVersionData);
    }

    @Override
    public Uni<SyncTenantVersionResponse> syncTenantVersion(final SyncTenantVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantVersion);
    }

    @Override
    public Uni<ViewTenantVersionsResponse> viewTenantVersions(final ViewTenantVersionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantVersions);
    }

    @Override
    public Uni<DeleteTenantVersionResponse> deleteTenantVersion(final DeleteTenantVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantVersion);
    }

    /*
    TenantFilesArchive
     */

    @Override
    public Uni<GetTenantFilesArchiveResponse> getTenantFilesArchive(final GetTenantFilesArchiveRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantFilesArchive);
    }

    @Override
    public Uni<FindTenantFilesArchiveResponse> findTenantFilesArchive(final FindTenantFilesArchiveRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findTenantFilesArchive);
    }

    @Override
    public Uni<ViewTenantFilesArchivesResponse> viewTenantFilesArchives(final ViewTenantFilesArchivesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantFilesArchives);
    }

    @Override
    public Uni<SyncTenantFilesArchiveResponse> syncTenantFilesArchive(final SyncTenantFilesArchiveRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantFilesArchive);
    }

    @Override
    public Uni<DeleteTenantFilesArchiveResponse> deleteTenantFilesArchive(
            final DeleteTenantFilesArchiveRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantFilesArchive);
    }

    /*
    TenantBuildRequest
     */

    @Override
    public Uni<GetTenantBuildRequestResponse> getTenantBuildRequest(
            final GetTenantBuildRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantBuildRequest);
    }

    @Override
    public Uni<ViewTenantBuildRequestsResponse> viewTenantBuildRequests(
            final ViewTenantBuildRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantBuildRequests);
    }

    @Override
    public Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequest(
            final SyncTenantBuildRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantBuildRequest);
    }

    @Override
    public Uni<DeleteTenantBuildRequestResponse> deleteTenantBuildRequest(
            final DeleteTenantBuildRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantBuildRequest);
    }

    /*
    TenantImage
     */

    @Override
    public Uni<GetTenantImageResponse> getTenantImage(final GetTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantImage);
    }

    @Override
    public Uni<FindTenantImageResponse> findTenantImage(final FindTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findTenantImage);
    }

    @Override
    public Uni<ViewTenantImagesResponse> viewTenantImages(final ViewTenantImagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantImages);
    }

    @Override
    public Uni<SyncTenantImageResponse> syncTenantImage(final SyncTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantImage);
    }

    @Override
    public Uni<DeleteTenantImageResponse> deleteTenantImage(final DeleteTenantImageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantImage);
    }

    /*
    TenantDeployment
     */

    @Override
    public Uni<GetTenantDeploymentResponse> getTenantDeployment(final GetTenantDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantDeployment);
    }

    @Override
    public Uni<GetTenantDeploymentDataResponse> getTenantDeploymentData(final GetTenantDeploymentDataRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantDeploymentData);
    }

    @Override
    public Uni<SelectTenantDeploymentResponse> selectTenantDeployment(final SelectTenantDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::selectTenantDeployment);
    }

    @Override
    public Uni<ViewTenantDeploymentsResponse> viewTenantDeployments(final ViewTenantDeploymentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantDeployments);
    }

    @Override
    public Uni<SyncTenantDeploymentResponse> syncTenantDeployment(final SyncTenantDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantDeployment);
    }

    @Override
    public Uni<DeleteTenantDeploymentResponse> deleteTenantDeployment(final DeleteTenantDeploymentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantDeployment);
    }

    /*
    TenantLobbyResource
     */

    @Override
    public Uni<GetTenantLobbyResourceResponse> execute(final GetTenantLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindTenantLobbyResourceResponse> execute(final FindTenantLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewTenantLobbyResourcesResponse> execute(
            final ViewTenantLobbyResourcesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncTenantLobbyResourceResponse> execute(
            final SyncTenantLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantLobbyResourceResponse> execute(
            final DeleteTenantLobbyResourceRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    TenantLobbyRef
     */

    @Override
    public Uni<GetTenantLobbyRefResponse> getTenantLobbyRef(final GetTenantLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantLobbyRef);
    }

    @Override
    public Uni<FindTenantLobbyRefResponse> findTenantLobbyRef(final FindTenantLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findTenantLobbyRef);
    }

    @Override
    public Uni<ViewTenantLobbyRefsResponse> viewTenantLobbyRefs(final ViewTenantLobbyRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantLobbyRefs);
    }

    @Override
    public Uni<SyncTenantLobbyRefResponse> syncTenantLobbyRef(final SyncTenantLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantLobbyRef);
    }

    @Override
    public Uni<DeleteTenantLobbyRefResponse> deleteTenantLobbyRef(final DeleteTenantLobbyRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantLobbyRef);
    }

    /*
    TenantMatchmakerRequest
     */

    @Override
    public Uni<GetTenantMatchmakerRequestResponse> getTenantMatchmakerRequest(
            final GetTenantMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantMatchmakerRequest);
    }

    @Override
    public Uni<FindTenantMatchmakerRequestResponse> findTenantMatchmakerRequest(
            final FindTenantMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findTenantMatchmakerRequest);
    }

    @Override
    public Uni<ViewTenantMatchmakerRequestsResponse> viewTenantMatchmakerRequests(
            final ViewTenantMatchmakerRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantMatchmakerRequests);
    }

    @Override
    public Uni<SyncTenantMatchmakerRequestResponse> syncTenantMatchmakerRequest(
            final SyncTenantMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantMatchmakerRequest);
    }

    @Override
    public Uni<DeleteTenantMatchmakerRequestResponse> deleteTenantMatchmakerRequest(
            final DeleteTenantMatchmakerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantMatchmakerRequest);
    }

    /*
    TenantMatchmakerRef
     */

    @Override
    public Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(GetTenantMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantMatchmakerRef);
    }

    @Override
    public Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(
            final FindTenantMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findTenantMatchmakerRef);
    }

    @Override
    public Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(
            final ViewTenantMatchmakerRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewTenantMatchmakerRefs);
    }

    @Override
    public Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(
            final SyncTenantMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantMatchmakerRef);
    }

    @Override
    public Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(
            final DeleteTenantMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantMatchmakerRef);
    }
}
