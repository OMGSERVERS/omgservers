package com.omgservers.application.module.internalModule.impl.service.logHelpService.impl.method;

import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.SyncLogHelpResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLogMethod {
    Uni<SyncLogHelpResponse> syncLog(SyncLogHelpRequest request);
}
