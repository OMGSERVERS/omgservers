package com.omgservers.application.module.internalModule.impl.service.syncInternalService;

import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncIndexOverServersInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;

public interface SyncInternalService {

    Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request);
}
