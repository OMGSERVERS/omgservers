package com.omgservers.module.internal.impl.service.syncService.impl.method.syncServiceAccountOverServers;

import com.omgservers.dto.internal.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountOverServersMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request);
}
