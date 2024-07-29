package com.omgservers.service.module.tenant.impl.service.stageService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface StageService {

    Uni<GetStageResponse> getStage(@Valid GetStageRequest request);

    Uni<SyncStageResponse> syncStage(@Valid SyncStageRequest request);

    Uni<ViewStagesResponse> viewStages(@Valid ViewStagesRequest request);

    Uni<DeleteStageResponse> deleteStage(@Valid DeleteStageRequest request);

    Uni<ViewStagePermissionsResponse> viewStagePermissions(@Valid ViewStagePermissionsRequest request);

    Uni<HasStagePermissionResponse> hasStagePermission(@Valid HasStagePermissionRequest request);

    Uni<SyncStagePermissionResponse> syncStagePermission(@Valid SyncStagePermissionRequest request);

    Uni<SyncStagePermissionResponse> syncStagePermissionWithIdempotency(@Valid SyncStagePermissionRequest request);

    Uni<DeleteStagePermissionResponse> deleteStagePermission(@Valid DeleteStagePermissionRequest request);

    Uni<ValidateStageSecretResponse> validateStageSecret(@Valid ValidateStageSecretRequest request);
}
