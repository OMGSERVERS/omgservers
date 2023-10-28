package com.omgservers.module.system.impl.service.logService.impl.method.syncLog;

import com.omgservers.model.dto.internal.SyncLogRequest;
import com.omgservers.model.dto.internal.SyncLogResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLogMethod {
    Uni<SyncLogResponse> syncLog(SyncLogRequest request);
}
