package com.omgservers.service.shard.tenant.impl.service.webService;

import com.omgservers.schema.module.tenant.tenant.*;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.*;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.*;
import com.omgservers.schema.module.tenant.tenantImage.*;
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

    Uni<GetTenantResponse> execute(GetTenantRequest request);

    Uni<GetTenantDataResponse> execute(GetTenantDataRequest request);

    Uni<SyncTenantResponse> execute(SyncTenantRequest request);

    Uni<DeleteTenantResponse> execute(DeleteTenantRequest request);

    /*
    TenantPermission
     */

    Uni<ViewTenantPermissionsResponse> execute(ViewTenantPermissionsRequest request);

    Uni<VerifyTenantPermissionExistsResponse> execute(VerifyTenantPermissionExistsRequest request);

    Uni<SyncTenantPermissionResponse> execute(SyncTenantPermissionRequest request);

    Uni<DeleteTenantPermissionResponse> execute(DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    Uni<GetTenantProjectResponse> execute(GetTenantProjectRequest request);

    Uni<GetTenantProjectDataResponse> execute(GetTenantProjectDataRequest request);

    Uni<SyncTenantProjectResponse> execute(SyncTenantProjectRequest request);

    Uni<ViewTenantProjectsResponse> execute(ViewTenantProjectsRequest request);

    Uni<DeleteTenantProjectResponse> execute(DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    Uni<ViewTenantProjectPermissionsResponse> execute(ViewTenantProjectPermissionsRequest request);

    Uni<VerifyTenantProjectPermissionExistsResponse> execute(VerifyTenantProjectPermissionExistsRequest request);

    Uni<SyncTenantProjectPermissionResponse> execute(SyncTenantProjectPermissionRequest request);

    Uni<DeleteTenantProjectPermissionResponse> execute(DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    Uni<GetTenantStageResponse> execute(GetTenantStageRequest request);

    Uni<GetTenantStageDataResponse> execute(GetTenantStageDataRequest request);

    Uni<SyncTenantStageResponse> execute(SyncTenantStageRequest request);

    Uni<ViewTenantStagesResponse> execute(ViewTenantStagesRequest request);

    Uni<DeleteTenantStageResponse> execute(DeleteTenantStageRequest request);

    /*
    TenantStagePermission
     */

    Uni<ViewTenantStagePermissionsResponse> execute(ViewTenantStagePermissionsRequest request);

    Uni<VerifyTenantStagePermissionExistsResponse> execute(VerifyTenantStagePermissionExistsRequest request);

    Uni<SyncTenantStagePermissionResponse> execute(SyncTenantStagePermissionRequest request);

    Uni<DeleteTenantStagePermissionResponse> execute(DeleteTenantStagePermissionRequest request);

    /*
    TenantVersion
     */

    Uni<GetTenantVersionResponse> execute(GetTenantVersionRequest request);

    Uni<GetTenantVersionConfigResponse> execute(GetTenantVersionConfigRequest request);

    Uni<GetTenantVersionDataResponse> execute(GetTenantVersionDataRequest request);

    Uni<SyncTenantVersionResponse> execute(SyncTenantVersionRequest request);

    Uni<ViewTenantVersionsResponse> execute(ViewTenantVersionsRequest request);

    Uni<DeleteTenantVersionResponse> execute(DeleteTenantVersionRequest request);

    /*
    TenantImage
     */

    Uni<GetTenantImageResponse> execute(GetTenantImageRequest request);

    Uni<FindTenantImageResponse> execute(FindTenantImageRequest request);

    Uni<ViewTenantImagesResponse> execute(ViewTenantImagesRequest request);

    Uni<SyncTenantImageResponse> execute(SyncTenantImageRequest request);

    Uni<DeleteTenantImageResponse> execute(DeleteTenantImageRequest request);

    /*
    TenantDeploymentResource
     */

    Uni<GetTenantDeploymentResourceResponse> execute(GetTenantDeploymentResourceRequest request);

    Uni<FindTenantDeploymentResourceResponse> execute(FindTenantDeploymentResourceRequest request);

    Uni<ViewTenantDeploymentResourcesResponse> execute(ViewTenantDeploymentResourcesRequest request);

    Uni<SyncTenantDeploymentResourceResponse> execute(SyncTenantDeploymentResourceRequest request);

    Uni<UpdateTenantDeploymentResourceStatusResponse> execute(UpdateTenantDeploymentResourceStatusRequest request);

    Uni<DeleteTenantDeploymentResourceResponse> execute(DeleteTenantDeploymentResourceRequest request);

    /*
    TenantDeploymentRef
     */

    Uni<GetTenantDeploymentRefResponse> execute(GetTenantDeploymentRefRequest request);

    Uni<FindTenantDeploymentRefResponse> execute(FindTenantDeploymentRefRequest request);

    Uni<ViewTenantDeploymentRefsResponse> execute(ViewTenantDeploymentRefsRequest request);

    Uni<SyncTenantDeploymentRefResponse> execute(SyncTenantDeploymentRefRequest request);

    Uni<DeleteTenantDeploymentRefResponse> execute(DeleteTenantDeploymentRefRequest request);
}
