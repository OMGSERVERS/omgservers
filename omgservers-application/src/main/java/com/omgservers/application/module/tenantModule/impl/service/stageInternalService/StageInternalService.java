package com.omgservers.application.module.tenantModule.impl.service.stageInternalService;

import com.omgservers.dto.tenantModule.DeleteStageInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageInternalRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface StageInternalService {

    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);
}
