package com.omgservers.service.shard.tenant.impl.service.webService;

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

    Uni<UpdateTenantLobbyResourceStatusResponse> execute(UpdateTenantLobbyResourceStatusRequest request);

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
    TenantMatchmakerResource
     */

    Uni<GetTenantMatchmakerResourceResponse> execute(GetTenantMatchmakerResourceRequest request);

    Uni<FindTenantMatchmakerResourceResponse> execute(FindTenantMatchmakerResourceRequest request);

    Uni<ViewTenantMatchmakerResourcesResponse> execute(ViewTenantMatchmakerResourcesRequest request);

    Uni<SyncTenantMatchmakerResourceResponse> execute(SyncTenantMatchmakerResourceRequest request);

    Uni<DeleteTenantMatchmakerResourceResponse> execute(DeleteTenantMatchmakerResourceRequest request);

    /*
    TenantMatchmakerRef
     */

    Uni<GetTenantMatchmakerRefResponse> getTenantMatchmakerRef(GetTenantMatchmakerRefRequest request);

    Uni<FindTenantMatchmakerRefResponse> findTenantMatchmakerRef(FindTenantMatchmakerRefRequest request);

    Uni<ViewTenantMatchmakerRefsResponse> viewTenantMatchmakerRefs(ViewTenantMatchmakerRefsRequest request);

    Uni<SyncTenantMatchmakerRefResponse> syncTenantMatchmakerRef(SyncTenantMatchmakerRefRequest request);

    Uni<DeleteTenantMatchmakerRefResponse> deleteTenantMatchmakerRef(DeleteTenantMatchmakerRefRequest request);
}
