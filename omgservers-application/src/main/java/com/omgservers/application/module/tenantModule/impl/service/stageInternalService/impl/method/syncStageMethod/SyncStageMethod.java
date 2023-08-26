package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod;

import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import io.smallrye.mutiny.Uni;

public interface SyncStageMethod {
    Uni<SyncStageInternalResponse> syncStage(SyncStageRoutedRequest request);
}
