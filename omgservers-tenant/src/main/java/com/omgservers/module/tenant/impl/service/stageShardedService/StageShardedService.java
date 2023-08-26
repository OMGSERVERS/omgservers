package com.omgservers.module.tenant.impl.service.stageShardedService;

import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface StageShardedService {

    Uni<GetStageInternalResponse> getStage(GetStageShardRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request);
}
