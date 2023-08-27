package com.omgservers.module.internal.impl.service.logService.impl.method.syncLog;

import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.dto.internal.SyncLogResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLogMethod {
    Uni<SyncLogResponse> syncLog(SyncLogRequest request);
}
