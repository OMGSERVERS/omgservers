package com.omgservers.module.system.impl.service.syncService.impl.method.syncServiceAccountOverServers;

import com.omgservers.dto.internal.SyncServiceAccountOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountOverServersMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request);
}
