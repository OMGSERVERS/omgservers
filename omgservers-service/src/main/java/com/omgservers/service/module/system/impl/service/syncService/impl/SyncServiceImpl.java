package com.omgservers.service.module.system.impl.service.syncService.impl;

import com.omgservers.service.module.system.impl.service.syncService.SyncService;
import com.omgservers.service.module.system.impl.service.syncService.impl.method.syncIndexOverServers.SyncIndexOverServersMethod;
import com.omgservers.service.module.system.impl.service.syncService.impl.method.syncServiceAccountOverServers.SyncServiceAccountOverServersMethod;
import com.omgservers.model.dto.system.SyncIndexOverServersRequest;
import com.omgservers.model.dto.system.SyncServiceAccountOverServersRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SyncServiceImpl implements SyncService {

    final SyncIndexOverServersMethod syncIndexOverServersMethod;
    final SyncServiceAccountOverServersMethod syncServiceAccountOverServersMethod;

    @Override
    public Uni<Void> syncIndex(SyncIndexOverServersRequest request) {
        return syncIndexOverServersMethod.syncIndex(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request) {
        return syncServiceAccountOverServersMethod.syncServiceAccount(request);
    }
}
