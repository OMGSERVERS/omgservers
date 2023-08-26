package com.omgservers.module.internal.impl.service.logService.impl;

import com.omgservers.module.internal.impl.service.logService.LogService;
import com.omgservers.module.internal.impl.service.logService.impl.method.syncLog.SyncLogMethod;
import com.omgservers.module.internal.impl.service.logService.impl.method.viewLogs.ViewLogsMethod;
import com.omgservers.dto.internalModule.SyncLogRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.SyncLogResponse;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LogServiceImpl implements LogService {

    final SyncLogMethod handleEventMethod;
    final ViewLogsMethod viewLogsMethod;

    @Override
    public Uni<SyncLogResponse> syncLog(final SyncLogRequest request) {
        return handleEventMethod.syncLog(request);
    }

    @Override
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return viewLogsMethod.viewLogs(request);
    }
}
