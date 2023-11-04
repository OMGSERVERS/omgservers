package com.omgservers.service.module.system.impl.service.syncService.impl.method.syncServiceAccountOverServers;

import com.omgservers.model.dto.system.SyncServiceAccountOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountOverServersMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request);
}
