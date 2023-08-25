package com.omgservers.base.impl.service.logHelpService.impl.method.syncLogMethod;

import com.omgservers.base.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.base.impl.service.logHelpService.response.SyncLogHelpResponse;
import io.smallrye.mutiny.Uni;

public interface SyncLogMethod {
    Uni<SyncLogHelpResponse> syncLog(SyncLogHelpRequest request);
}
