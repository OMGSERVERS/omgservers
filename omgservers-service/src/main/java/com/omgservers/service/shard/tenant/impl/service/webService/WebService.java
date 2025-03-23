package com.omgservers.service.shard.tenant.impl.service.webService;

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
    TenantFilesArchive
     */

    Uni<GetTenantFilesArchiveResponse> getTenantFilesArchive(GetTenantFilesArchiveRequest request);

    Uni<FindTenantFilesArchiveResponse> findTenantFilesArchive(FindTenantFilesArchiveRequest request);

    Uni<ViewTenantFilesArchivesResponse> viewTenantFilesArchives(ViewTenantFilesArchivesRequest request);

    Uni<SyncTenantFilesArchiveResponse> syncTenantFilesArchive(SyncTenantFilesArchiveRequest request);

    Uni<DeleteTenantFilesArchiveResponse> deleteTenantFilesArchive(DeleteTenantFilesArchiveRequest request);

    /*
    TenantBuildRequest
     */

    Uni<GetTenantBuildRequestResponse> getTenantBuildRequest(GetTenantBuildRequestRequest request);

    Uni<ViewTenantBuildRequestsResponse> viewTenantBuildRequests(ViewTenantBuildRequestsRequest request);

    Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequest(SyncTenantBuildRequestRequest request);

    Uni<DeleteTenantBuildRequestResponse> deleteTenantBuildRequest(DeleteTenantBuildRequestRequest request);

    /*
    TenantImage
     */

    Uni<GetTenantImageResponse> getTenantImage(GetTenantImageRequest request);

    Uni<FindTenantImageResponse> findTenantImage(FindTenantImageRequest request);

    Uni<ViewTenantImagesResponse> viewTenantImages(ViewTenantImagesRequest request);

    Uni<SyncTenantImageResponse> syncTenantImage(SyncTenantImageRequest request);

    Uni<DeleteTenantImageResponse> deleteTenantImage(DeleteTenantImageRequest request);

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
    TenantLobbyResource
     */

    Uni<GetTenantLobbyResourceResponse> execute(GetTenantLobbyResourceRequest request);

    Uni<FindTenantLobbyResourceResponse> execute(FindTenantLobbyResourceRequest request);

    Uni<ViewTenantLobbyResourcesResponse> execute(ViewTenantLobbyResourcesRequest request);

    Uni<SyncTenantLobbyResourceResponse> execute(SyncTenantLobbyResourceRequest request);

    Uni<DeleteTenantLobbyResourceResponse> execute(DeleteTenantLobbyResourceRequest request);

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
