package com.omgservers.service.shard.tenant.service.testInterface;

import com.omgservers.schema.shard.tenant.tenant.*;
import com.omgservers.schema.shard.tenant.tenantImage.*;
import com.omgservers.schema.shard.tenant.tenantPermission.*;
import com.omgservers.schema.shard.tenant.tenantProject.*;
import com.omgservers.schema.shard.tenant.tenantProjectPermission.*;
import com.omgservers.schema.shard.tenant.tenantStage.*;
import com.omgservers.schema.shard.tenant.tenantStagePermission.*;
import com.omgservers.schema.shard.tenant.tenantVersion.*;
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
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantDataResponse getTenantData(final GetTenantDataRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantResponse syncTenant(final SyncTenantRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantResponse deleteTenant(final DeleteTenantRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantPermission
     */

    public ViewTenantPermissionsResponse viewTenantPermissions(final ViewTenantPermissionsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantPermissionExistsResponse verifyTenantPermissionExists(
            final VerifyTenantPermissionExistsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantPermissionResponse syncTenantPermission(final SyncTenantPermissionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantPermissionResponse deleteTenantPermission(final DeleteTenantPermissionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantProject
     */

    public GetTenantProjectResponse getTenantProject(final GetTenantProjectRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantProjectDataResponse getTenantProjectData(final GetTenantProjectDataRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantProjectResponse syncTenantProject(final SyncTenantProjectRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantProjectsResponse viewTenantProjects(final ViewTenantProjectsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantProjectResponse deleteTenantProject(final DeleteTenantProjectRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantProjectPermission
     */

    public ViewTenantProjectPermissionsResponse viewTenantProjectPermissions(
            final ViewTenantProjectPermissionsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantProjectPermissionExistsResponse verifyTenantProjectPermissionExists(
            final VerifyTenantProjectPermissionExistsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantProjectPermissionResponse syncTenantProjectPermission(
            final SyncTenantProjectPermissionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantProjectPermissionResponse deleteTenantProjectPermission(
            final DeleteTenantProjectPermissionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantStage
     */

    public GetTenantStageResponse getTenantStage(final GetTenantStageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantStageDataResponse getTenantStageData(final GetTenantStageDataRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStageResponse syncTenantStage(final SyncTenantStageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantStagesResponse viewTenantStages(final ViewTenantStagesRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantStageResponse deleteTenantStage(final DeleteTenantStageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantStagePermission
     */

    public ViewTenantStagePermissionsResponse viewTenantStagePermissions(
            final ViewTenantStagePermissionsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantStagePermissionExistsResponse verifyTenantStagePermissionExists(
            final VerifyTenantStagePermissionExistsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStagePermissionResponse syncTenantStagePermission(final SyncTenantStagePermissionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStagePermissionResponse syncTenantStagePermissionWithIdempotency(
            final SyncTenantStagePermissionRequest request) {
        return tenantService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantStagePermissionResponse deleteTenantStagePermission(
            final DeleteTenantStagePermissionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantVersion
     */

    public GetTenantVersionResponse getTenantVersion(final GetTenantVersionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantVersionConfigResponse getTenantVersionConfig(final GetTenantVersionConfigRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetTenantVersionDataResponse getTenantVersionData(final GetTenantVersionDataRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantVersionResponse syncTenantVersion(final SyncTenantVersionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantVersionsResponse viewTenantVersions(final ViewTenantVersionsRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantVersionResponse deleteTenantVersion(final DeleteTenantVersionRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    /*
    TenantImage
     */

    public GetTenantImageResponse getTenantImage(final GetTenantImageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindTenantImageResponse findTenantImage(final FindTenantImageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantImagesResponse viewTenantImages(final ViewTenantImagesRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantImageResponse syncTenantImage(final SyncTenantImageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantImageResponse syncTenantImageWithIdempotency(final SyncTenantImageRequest request) {
        return tenantService.executeWithIdempotency(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantImageResponse deleteTenantImage(final DeleteTenantImageRequest request) {
        return tenantService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
