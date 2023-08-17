package com.omgservers.application.module.adminModule.impl.service.adminHelpService.impl.method.collectLogsMethod;

import com.omgservers.application.module.adminModule.impl.service.adminHelpService.request.CollectLogsHelpRequest;
import com.omgservers.application.module.adminModule.impl.service.adminHelpService.response.CollectLogsHelpResponse;
import io.smallrye.mutiny.Uni;

public interface CollectLogsMethod {
    Uni<CollectLogsHelpResponse> collectLogs(CollectLogsHelpRequest request);
}
