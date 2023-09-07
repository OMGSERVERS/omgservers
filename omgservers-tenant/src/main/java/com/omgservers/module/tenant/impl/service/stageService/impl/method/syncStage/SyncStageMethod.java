package com.omgservers.module.tenant.impl.service.stageService.impl.method.syncStage;

import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageResponse> syncStage(SyncStageRequest request);
}
