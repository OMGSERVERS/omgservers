package com.omgservers.module.system.impl.service.syncService;

import com.omgservers.model.dto.internal.SyncIndexOverServersRequest;
import com.omgservers.model.dto.internal.SyncServiceAccountOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncService {

    Uni<Void> syncIndex(SyncIndexOverServersRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request);
}
