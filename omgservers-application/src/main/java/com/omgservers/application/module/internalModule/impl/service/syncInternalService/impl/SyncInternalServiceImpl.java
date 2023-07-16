package com.omgservers.application.module.internalModule.impl.service.syncInternalService.impl;

import com.omgservers.application.module.internalModule.impl.service.syncInternalService.SyncInternalService;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.impl.method.syncIndexOverServersInternalMethod.SyncIndexOverServersInternalMethod;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.impl.method.syncServiceAccountOverServersInternalMethod.SyncServiceAccountOverServersInternalMethod;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncIndexOverServersInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SyncInternalServiceImpl implements SyncInternalService {

    final SyncIndexOverServersInternalMethod syncIndexOverServersInternalMethod;
    final SyncServiceAccountOverServersInternalMethod syncServiceAccountOverServersInternalMethod;

    @Override
    public Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request) {
        return syncIndexOverServersInternalMethod.syncIndex(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request) {
        return syncServiceAccountOverServersInternalMethod.syncServiceAccount(request);
    }
}
