package com.omgservers.module.internal.impl.service.syncService;

import com.omgservers.dto.internal.SyncIndexOverServersRequest;
import com.omgservers.dto.internal.SyncServiceAccountOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncService {

    Uni<Void> syncIndex(SyncIndexOverServersRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request);
}
