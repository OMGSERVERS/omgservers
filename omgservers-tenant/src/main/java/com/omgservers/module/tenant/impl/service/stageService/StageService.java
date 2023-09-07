package com.omgservers.module.tenant.impl.service.stageService;

import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.dto.tenant.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;

public interface StageService {

    Uni<GetStageResponse> getStage(GetStageRequest request);

    Uni<SyncStageResponse> syncStage(SyncStageRequest request);

    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);

    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);

    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);

    Uni<ValidateStageSecretResponse> validateStageSecret(ValidateStageSecretRequest request);
}
