package com.omgservers.module.system.impl.service.logService.impl;

import com.omgservers.module.system.impl.service.logService.impl.method.syncLog.SyncLogMethod;
import com.omgservers.module.system.impl.service.logService.impl.method.viewLogs.ViewLogsMethod;
import com.omgservers.module.system.impl.service.logService.LogService;
import com.omgservers.model.dto.internal.SyncLogRequest;
import com.omgservers.model.dto.internal.ViewLogRequest;
import com.omgservers.model.dto.internal.SyncLogResponse;
import com.omgservers.model.dto.internal.ViewLogsResponse;
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