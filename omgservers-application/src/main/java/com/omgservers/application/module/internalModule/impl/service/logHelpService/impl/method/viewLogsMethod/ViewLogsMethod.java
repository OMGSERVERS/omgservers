package com.omgservers.application.module.internalModule.impl.service.logHelpService.impl.method.viewLogsMethod;

import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ViewLogsMethod {
    Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request);
}
