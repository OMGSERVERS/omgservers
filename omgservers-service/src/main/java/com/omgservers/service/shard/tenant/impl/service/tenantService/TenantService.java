package com.omgservers.service.shard.tenant.impl.service.tenantService;

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
    TenantFilesArchive
     */

    Uni<GetTenantFilesArchiveResponse> getTenantFilesArchive(@Valid GetTenantFilesArchiveRequest request);

    Uni<FindTenantFilesArchiveResponse> findTenantFilesArchive(@Valid FindTenantFilesArchiveRequest request);

    Uni<ViewTenantFilesArchivesResponse> viewTenantFilesArchives(@Valid ViewTenantFilesArchivesRequest request);

    Uni<SyncTenantFilesArchiveResponse> syncTenantFilesArchive(@Valid SyncTenantFilesArchiveRequest request);

    Uni<DeleteTenantFilesArchiveResponse> deleteTenantFilesArchive(@Valid DeleteTenantFilesArchiveRequest request);

    /*
    TenantBuildRequest
     */

    Uni<GetTenantBuildRequestResponse> getTenantBuildRequest(@Valid GetTenantBuildRequestRequest request);

    Uni<ViewTenantBuildRequestsResponse> viewTenantBuildRequests(
            @Valid ViewTenantBuildRequestsRequest request);

    Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequest(@Valid SyncTenantBuildRequestRequest request);

    Uni<SyncTenantBuildRequestResponse> syncTenantBuildRequestWithIdempotency(
            @Valid SyncTenantBuildRequestRequest request);

    Uni<DeleteTenantBuildRequestResponse> deleteTenantBuildRequest(
            @Valid DeleteTenantBuildRequestRequest request);

    /*
    TenantImage
     */

    Uni<GetTenantImageResponse> getTenantImage(@Valid GetTenantImageRequest request);

    Uni<FindTenantImageResponse> findTenantImage(@Valid FindTenantImageRequest request);

    Uni<ViewTenantImagesResponse> viewTenantImages(@Valid ViewTenantImagesRequest request);

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
    TenantLobbyResource
     */

    Uni<GetTenantLobbyResourceResponse> execute(@Valid GetTenantLobbyResourceRequest request);

    Uni<FindTenantLobbyResourceResponse> execute(@Valid FindTenantLobbyResourceRequest request);

    Uni<ViewTenantLobbyResourcesResponse> execute(@Valid ViewTenantLobbyResourcesRequest request);

    Uni<SyncTenantLobbyResourceResponse> execute(@Valid SyncTenantLobbyResourceRequest request);

    Uni<SyncTenantLobbyResourceResponse> executeWithIdempotency(@Valid SyncTenantLobbyResourceRequest request);

    Uni<DeleteTenantLobbyResourceResponse> execute(@Valid DeleteTenantLobbyResourceRequest request);

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
    TenantMatchmakerResource
     */

    Uni<GetTenantMatchmakerResourceResponse> execute(@Valid GetTenantMatchmakerResourceRequest request);

    Uni<FindTenantMatchmakerResourceResponse> execute(@Valid FindTenantMatchmakerResourceRequest request);

    Uni<ViewTenantMatchmakerResourcesResponse> execute(@Valid ViewTenantMatchmakerResourcesRequest request);

    Uni<SyncTenantMatchmakerResourceResponse> execute(@Valid SyncTenantMatchmakerResourceRequest request);

    Uni<SyncTenantMatchmakerResourceResponse> executeWithIdempotency(@Valid SyncTenantMatchmakerResourceRequest request);

    Uni<DeleteTenantMatchmakerResourceResponse> execute(@Valid DeleteTenantMatchmakerResourceRequest request);

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
