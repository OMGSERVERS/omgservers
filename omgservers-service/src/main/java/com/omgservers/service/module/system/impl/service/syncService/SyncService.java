package com.omgservers.service.module.system.impl.service.syncService;

import com.omgservers.model.dto.system.SyncIndexOverServersRequest;
import com.omgservers.model.dto.system.SyncServiceAccountOverServersRequest;
import io.smallrye.mutiny.Uni;

public interface SyncService {

    Uni<Void> syncIndex(SyncIndexOverServersRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request);
}
