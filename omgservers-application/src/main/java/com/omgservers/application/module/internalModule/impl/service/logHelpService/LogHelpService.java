package com.omgservers.application.module.internalModule.impl.service.logHelpService;

import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.SyncLogHelpResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface LogHelpService {

    Uni<SyncLogHelpResponse> syncLog(SyncLogHelpRequest request);

    default SyncLogHelpResponse syncLog(long timeout, SyncLogHelpRequest request) {
        return syncLog(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
