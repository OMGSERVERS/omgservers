package com.omgservers.module.system.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.model.dto.internal.SyncIndexOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersMethod {
    Uni<Void> syncIndex(SyncIndexOverServersRequest request);
}