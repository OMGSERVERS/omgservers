package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStage;

import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request);
}
