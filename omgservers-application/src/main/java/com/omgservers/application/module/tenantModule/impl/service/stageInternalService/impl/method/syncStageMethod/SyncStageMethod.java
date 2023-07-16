package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<Void> syncStage(SyncStageInternalRequest request);
}
