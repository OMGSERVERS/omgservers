package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStage;

import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request);
}
