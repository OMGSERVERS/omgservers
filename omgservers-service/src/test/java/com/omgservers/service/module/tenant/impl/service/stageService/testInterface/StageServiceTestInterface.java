package com.omgservers.service.module.tenant.impl.service.stageService.testInterface;

import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.DeleteTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.DeleteTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.SyncTenantStagePermissionResponse;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantStage.ValidateTenantStageSecretRequest;
import com.omgservers.schema.module.tenant.tenantStage.ValidateTenantStageSecretResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.ViewTenantStagePermissionsResponse;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.module.tenant.impl.service.stageService.StageService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final StageService stageService;

    public GetTenantStageResponse getStage(GetTenantStageRequest request) {
        return stageService.getStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStageResponse syncStage(SyncTenantStageRequest request) {
        return stageService.syncStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantStagesResponse viewStages(ViewTenantStagesRequest request) {
        return stageService.viewStages(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantStageResponse deleteStage(DeleteTenantStageRequest request) {
        return stageService.deleteStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantStagePermissionsResponse viewStagePermissions(ViewTenantStagePermissionsRequest request) {
        return stageService.viewStagePermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantStagePermissionExistsResponse hasStagePermission(VerifyTenantStagePermissionExistsRequest request) {
        return stageService.verifyTenantStagePermissionExists(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantStagePermissionResponse syncStagePermission(SyncTenantStagePermissionRequest request) {
        return stageService.syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantStagePermissionResponse deleteStagePermission(DeleteTenantStagePermissionRequest request) {
        return stageService.deleteStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ValidateTenantStageSecretResponse validateStageSecret(ValidateTenantStageSecretRequest request) {
        return stageService.validateStageSecret(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
