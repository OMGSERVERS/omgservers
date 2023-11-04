package com.omgservers.service.module.system.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.model.dto.system.SyncIndexOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersMethod {
    Uni<Void> syncIndex(SyncIndexOverServersRequest request);
}
