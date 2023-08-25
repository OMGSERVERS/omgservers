package com.omgservers.base.impl.service.syncInternalService.impl.method.syncIndexOverServersInternalMethod;

import com.omgservers.dto.internalModule.SyncIndexOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncIndexOverServersInternalMethod {
    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);
}
