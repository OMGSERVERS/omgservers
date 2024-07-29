package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.syncStage;

import com.omgservers.schema.module.tenant.SyncStageRequest;
import com.omgservers.schema.module.tenant.SyncStageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageResponse> syncStage(SyncStageRequest request);
}
