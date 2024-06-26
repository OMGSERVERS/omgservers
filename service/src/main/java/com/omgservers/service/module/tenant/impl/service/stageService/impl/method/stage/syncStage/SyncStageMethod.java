package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.syncStage;

import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageResponse> syncStage(SyncStageRequest request);
}
