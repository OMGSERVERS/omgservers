package com.omgservers.module.internal.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.dto.internal.SyncIndexOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersMethod {
    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);
}
