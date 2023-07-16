package com.omgservers.application.module.internalModule.impl.service.syncInternalService.impl.method.syncServiceAccountOverServersInternalMethod;

import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncServiceAccountOverServersInternalMethod {
    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request);
}
