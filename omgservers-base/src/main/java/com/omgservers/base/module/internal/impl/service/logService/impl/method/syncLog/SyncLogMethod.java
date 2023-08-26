package com.omgservers.base.module.internal.impl.service.logService.impl.method.syncLog;

import com.omgservers.dto.internalModule.SyncLogRequest;
import com.omgservers.dto.internalModule.SyncLogResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLogMethod {
    Uni<SyncLogResponse> syncLog(SyncLogRequest request);
}
