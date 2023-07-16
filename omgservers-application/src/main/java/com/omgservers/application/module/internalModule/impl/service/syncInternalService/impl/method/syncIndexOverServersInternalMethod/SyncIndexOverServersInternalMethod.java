package com.omgservers.application.module.internalModule.impl.service.syncInternalService.impl.method.syncIndexOverServersInternalMethod;

import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncIndexOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersInternalMethod {
    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);
}
