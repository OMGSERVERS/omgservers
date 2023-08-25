package com.omgservers.base.impl.service.syncInternalService.impl.method.syncServiceAccountOverServersInternalMethod;

import com.omgservers.dto.internalModule.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountOverServersInternalMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request);
}
