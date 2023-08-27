package com.omgservers.module.tenant.impl.service.stageShardedService;

import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageInternalResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageInternalResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface StageShardedService {

    Uni<GetStageInternalResponse> getStage(GetStageShardedRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageShardedRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardedRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardedRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardedRequest request);
}
