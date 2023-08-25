package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.collectLogsMethod;

import com.omgservers.dto.adminModule.CollectLogsAdminRequest;
import com.omgservers.dto.adminModule.CollectLogsAdminResponse;
import io.smallrye.mutiny.Uni;

public interface CollectLogsMethod {
    Uni<CollectLogsAdminResponse> collectLogs(CollectLogsAdminRequest request);
}
