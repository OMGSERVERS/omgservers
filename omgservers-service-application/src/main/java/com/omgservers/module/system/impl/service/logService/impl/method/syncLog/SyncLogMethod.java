package com.omgservers.module.system.impl.service.logService.impl.method.syncLog;

import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.SyncLogResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLogMethod {
    Uni<SyncLogResponse> syncLog(SyncLogRequest request);
}
