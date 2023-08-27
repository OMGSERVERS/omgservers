package com.omgservers.module.internal.impl.service.logService;

import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.SyncLogResponse;
import com.omgservers.dto.internal.ViewLogsResponse;
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
