package com.omgservers.service.module.tenant.impl.service.stageService.testInterface;

import com.omgservers.schema.module.tenant.DeleteStagePermissionRequest;
import com.omgservers.schema.module.tenant.DeleteStagePermissionResponse;
import com.omgservers.schema.module.tenant.DeleteStageRequest;
import com.omgservers.schema.module.tenant.DeleteStageResponse;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import com.omgservers.schema.module.tenant.SyncStagePermissionRequest;
import com.omgservers.schema.module.tenant.SyncStagePermissionResponse;
import com.omgservers.schema.module.tenant.SyncStageRequest;
import com.omgservers.schema.module.tenant.SyncStageResponse;
import com.omgservers.schema.module.tenant.ValidateStageSecretRequest;
import com.omgservers.schema.module.tenant.ValidateStageSecretResponse;
import com.omgservers.schema.module.tenant.ViewStagePermissionsRequest;
import com.omgservers.schema.module.tenant.ViewStagePermissionsResponse;
import com.omgservers.schema.module.tenant.ViewStagesRequest;
import com.omgservers.schema.module.tenant.ViewStagesResponse;
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

    public GetStageResponse getStage(GetStageRequest request) {
        return stageService.getStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncStageResponse syncStage(SyncStageRequest request) {
        return stageService.syncStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewStagesResponse viewStages(ViewStagesRequest request) {
        return stageService.viewStages(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteStageResponse deleteStage(DeleteStageRequest request) {
        return stageService.deleteStage(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewStagePermissionsResponse viewStagePermissions(ViewStagePermissionsRequest request) {
        return stageService.viewStagePermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public HasStagePermissionResponse hasStagePermission(HasStagePermissionRequest request) {
        return stageService.hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncStagePermissionResponse syncStagePermission(SyncStagePermissionRequest request) {
        return stageService.syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteStagePermissionResponse deleteStagePermission(DeleteStagePermissionRequest request) {
        return stageService.deleteStagePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ValidateStageSecretResponse validateStageSecret(ValidateStageSecretRequest request) {
        return stageService.validateStageSecret(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
