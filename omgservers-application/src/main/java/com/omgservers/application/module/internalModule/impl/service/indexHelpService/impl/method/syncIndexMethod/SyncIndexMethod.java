package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.syncIndexMethod;

import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<Void> syncIndex(SyncIndexHelpRequest request);
}
