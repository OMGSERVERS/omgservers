package com.omgservers.module.system.impl.service.logService;

import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.SyncLogResponse;
import com.omgservers.model.dto.system.ViewLogsResponse;
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
