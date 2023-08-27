package com.omgservers.module.internal.impl.service.logService.impl.method.viewLogs;

import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewLogsMethod {
    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
