package com.omgservers.service.module.system.impl.service.logService.impl.method.viewLogs;

import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewLogsMethod {
    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);
}
