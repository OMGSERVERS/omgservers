package com.omgservers.base.module.internal.impl.service.syncService;

import com.omgservers.dto.internalModule.SyncIndexOverServersInternalRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncService {

    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request);
}
