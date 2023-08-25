package com.omgservers.base.impl.service.logHelpService.impl.method.viewLogsMethod;

import com.omgservers.base.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.base.impl.service.logHelpService.response.ViewLogsHelpResponse;
import io.smallrye.mutiny.Uni;

public interface ViewLogsMethod {
    Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request);
}
