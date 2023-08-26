package com.omgservers.module.internal.impl.service.logService.impl.method.viewLogs;

import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewLogsMethod {
    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
