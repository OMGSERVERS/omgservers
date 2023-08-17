package com.omgservers.application.module.internalModule.impl.service.logHelpService.impl;

import com.omgservers.application.module.internalModule.impl.service.logHelpService.LogHelpService;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.impl.method.SyncLogMethod;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.SyncLogHelpResponse;
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

    @Override
    public Uni<SyncLogHelpResponse> syncLog(SyncLogHelpRequest request) {
        return handleEventMethod.syncLog(request);
    }
}
