package com.omgservers.module.system.impl.service.logService;

import com.omgservers.model.dto.internal.SyncLogRequest;
import com.omgservers.model.dto.internal.ViewLogRequest;
import com.omgservers.model.dto.internal.SyncLogResponse;
import com.omgservers.model.dto.internal.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface LogService {

    Uni<SyncLogResponse> syncLog(SyncLogRequest request);

    default SyncLogResponse syncLog(long timeout, SyncLogRequest request) {
        return syncLog(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);

    default ViewLogsResponse viewLogs(long timeout, ViewLogRequest request) {
        return viewLogs(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
