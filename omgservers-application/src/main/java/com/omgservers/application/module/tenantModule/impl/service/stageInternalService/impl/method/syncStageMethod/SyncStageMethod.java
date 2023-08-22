package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod;

import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request);
}
