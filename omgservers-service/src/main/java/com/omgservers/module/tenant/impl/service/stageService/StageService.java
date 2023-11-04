package com.omgservers.module.tenant.impl.service.stageService;

import com.omgservers.model.dto.tenant.DeleteStageRequest;
import com.omgservers.model.dto.tenant.DeleteStageResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import com.omgservers.model.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.model.dto.tenant.ValidateStageSecretResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface StageService {

    Uni<GetStageResponse> getStage(@Valid GetStageRequest request);

    Uni<SyncStageResponse> syncStage(@Valid SyncStageRequest request);

    Uni<DeleteStageResponse> deleteStage(@Valid DeleteStageRequest request);

    Uni<HasStagePermissionResponse> hasStagePermission(@Valid HasStagePermissionRequest request);

    Uni<SyncStagePermissionResponse> syncStagePermission(@Valid SyncStagePermissionRequest request);

    Uni<ValidateStageSecretResponse> validateStageSecret(@Valid ValidateStageSecretRequest request);
}
