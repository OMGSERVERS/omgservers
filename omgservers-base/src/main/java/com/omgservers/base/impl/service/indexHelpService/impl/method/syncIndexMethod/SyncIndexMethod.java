package com.omgservers.base.impl.service.indexHelpService.impl.method.syncIndexMethod;

import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<Void> syncIndex(SyncIndexHelpRequest request);
}
