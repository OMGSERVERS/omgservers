package com.omgservers.module.system.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.dto.internal.SyncIndexOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersMethod {
    Uni<Void> syncIndex(SyncIndexOverServersRequest request);
}