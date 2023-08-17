package com.omgservers.application.module.internalModule.impl.service.logHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.logHelpService.LogHelpService;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.impl.method.syncLogMethod.SyncLogMethod;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.impl.method.viewLogsMethod.ViewLogsMethod;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.SyncLogHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LogHelpServiceImpl implements LogHelpService {

    final SyncLogMethod handleEventMethod;
    final ViewLogsMethod viewLogsMethod;

    @Override
    public Uni<SyncLogHelpResponse> syncLog(final SyncLogHelpRequest request) {
        return handleEventMethod.syncLog(request);
    }

    @Override
    public Uni<ViewLogsHelpResponse> viewLogs(final ViewLogsHelpRequest request) {
        return viewLogsMethod.viewLogs(request);
    }
}
