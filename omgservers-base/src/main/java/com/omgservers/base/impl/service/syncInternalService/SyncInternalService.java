package com.omgservers.base.impl.service.syncInternalService;

import com.omgservers.dto.internalModule.SyncIndexOverServersInternalRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncInternalService {

    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request);
}
