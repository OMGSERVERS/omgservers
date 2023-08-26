package com.omgservers.base.module.internal.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.dto.internalModule.SyncIndexOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersMethod {
    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);
}
