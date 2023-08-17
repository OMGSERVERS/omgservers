package com.omgservers.application.module.internalModule.impl.service.logHelpService;

import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.SyncLogHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface LogHelpService {

    Uni<SyncLogHelpResponse> syncLog(SyncLogHelpRequest request);

    default SyncLogHelpResponse syncLog(long timeout, SyncLogHelpRequest request) {
        return syncLog(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request);

    default ViewLogsHelpResponse viewLogs(long timeout, ViewLogsHelpRequest request) {
        return viewLogs(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
