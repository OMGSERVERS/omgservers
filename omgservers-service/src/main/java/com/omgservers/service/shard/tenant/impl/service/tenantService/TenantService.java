package com.omgservers.service.shard.tenant.impl.service.tenantService;

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
import jakarta.validation.Valid;

public interface TenantService {

    /*
    Tenant
     */

    Uni<GetTenantResponse> execute(@Valid GetTenantRequest request);

    Uni<GetTenantDataResponse> execute(@Valid GetTenantDataRequest request);

    Uni<SyncTenantResponse> execute(@Valid SyncTenantRequest request);

    Uni<DeleteTenantResponse> execute(@Valid DeleteTenantRequest request);

    /*
    TenantPermission
     */

    Uni<ViewTenantPermissionsResponse> execute(@Valid ViewTenantPermissionsRequest request);

    Uni<VerifyTenantPermissionExistsResponse> execute(@Valid VerifyTenantPermissionExistsRequest request);

    Uni<SyncTenantPermissionResponse> execute(@Valid SyncTenantPermissionRequest request);

    Uni<DeleteTenantPermissionResponse> execute(@Valid DeleteTenantPermissionRequest request);

    /*
    TenantProject
     */

    Uni<GetTenantProjectResponse> execute(@Valid GetTenantProjectRequest request);

    Uni<GetTenantProjectDataResponse> execute(@Valid GetTenantProjectDataRequest request);

    Uni<SyncTenantProjectResponse> execute(@Valid SyncTenantProjectRequest request);

    Uni<ViewTenantProjectsResponse> execute(@Valid ViewTenantProjectsRequest request);

    Uni<DeleteTenantProjectResponse> execute(@Valid DeleteTenantProjectRequest request);

    /*
    TenantProjectPermission
     */

    Uni<ViewTenantProjectPermissionsResponse> execute(@Valid ViewTenantProjectPermissionsRequest request);

    Uni<VerifyTenantProjectPermissionExistsResponse> execute(@Valid VerifyTenantProjectPermissionExistsRequest request);

    Uni<SyncTenantProjectPermissionResponse> execute(@Valid SyncTenantProjectPermissionRequest request);

    Uni<SyncTenantProjectPermissionResponse> executeWithIdempotency(@Valid SyncTenantProjectPermissionRequest request);

    Uni<DeleteTenantProjectPermissionResponse> execute(@Valid DeleteTenantProjectPermissionRequest request);

    /*
    TenantStage
     */

    Uni<GetTenantStageResponse> execute(@Valid GetTenantStageRequest request);

    Uni<GetTenantStageDataResponse> execute(@Valid GetTenantStageDataRequest request);

    Uni<SyncTenantStageResponse> execute(@Valid SyncTenantStageRequest request);

    Uni<ViewTenantStagesResponse> execute(@Valid ViewTenantStagesRequest request);

    Uni<DeleteTenantStageResponse> execute(@Valid DeleteTenantStageRequest request);

    /*
    TenantStagePermission
     */

    Uni<ViewTenantStagePermissionsResponse> execute(@Valid ViewTenantStagePermissionsRequest request);

    Uni<VerifyTenantStagePermissionExistsResponse> execute(@Valid VerifyTenantStagePermissionExistsRequest request);

    Uni<SyncTenantStagePermissionResponse> execute(@Valid SyncTenantStagePermissionRequest request);

    Uni<SyncTenantStagePermissionResponse> executeWithIdempotency(@Valid SyncTenantStagePermissionRequest request);

    Uni<DeleteTenantStagePermissionResponse> execute(@Valid DeleteTenantStagePermissionRequest request);

    /*
    TenantVersion
     */

    Uni<GetTenantVersionResponse> execute(@Valid GetTenantVersionRequest request);

    Uni<GetTenantVersionConfigResponse> execute(@Valid GetTenantVersionConfigRequest request);

    Uni<GetTenantVersionDataResponse> execute(@Valid GetTenantVersionDataRequest request);

    Uni<SyncTenantVersionResponse> execute(@Valid SyncTenantVersionRequest request);

    Uni<ViewTenantVersionsResponse> execute(@Valid ViewTenantVersionsRequest request);

    Uni<DeleteTenantVersionResponse> execute(@Valid DeleteTenantVersionRequest request);

    /*
    TenantImage
     */

    Uni<GetTenantImageResponse> execute(@Valid GetTenantImageRequest request);

    Uni<FindTenantImageResponse> execute(@Valid FindTenantImageRequest request);

    Uni<ViewTenantImagesResponse> execute(@Valid ViewTenantImagesRequest request);

    Uni<SyncTenantImageResponse> execute(@Valid SyncTenantImageRequest request);

    Uni<SyncTenantImageResponse> executeWithIdempotency(@Valid SyncTenantImageRequest request);

    Uni<DeleteTenantImageResponse> execute(@Valid DeleteTenantImageRequest request);

    /*
    TenantDeploymentResource
     */

    Uni<GetTenantDeploymentResourceResponse> execute(@Valid GetTenantDeploymentResourceRequest request);

    Uni<FindTenantDeploymentResourceResponse> execute(@Valid FindTenantDeploymentResourceRequest request);

    Uni<ViewTenantDeploymentResourcesResponse> execute(@Valid ViewTenantDeploymentResourcesRequest request);

    Uni<SyncTenantDeploymentResourceResponse> execute(@Valid SyncTenantDeploymentResourceRequest request);

    Uni<UpdateTenantDeploymentResourceStatusResponse> execute(@Valid UpdateTenantDeploymentResourceStatusRequest request);

    Uni<DeleteTenantDeploymentResourceResponse> execute(@Valid DeleteTenantDeploymentResourceRequest request);

    /*
    TenantDeploymentRef
     */

    Uni<GetTenantDeploymentRefResponse> execute(@Valid GetTenantDeploymentRefRequest request);

    Uni<FindTenantDeploymentRefResponse> execute(@Valid FindTenantDeploymentRefRequest request);

    Uni<ViewTenantDeploymentRefsResponse> execute(@Valid ViewTenantDeploymentRefsRequest request);

    Uni<SyncTenantDeploymentRefResponse> execute(@Valid SyncTenantDeploymentRefRequest request);

    Uni<DeleteTenantDeploymentRefResponse> execute(@Valid DeleteTenantDeploymentRefRequest request);
}
