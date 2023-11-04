package com.omgservers.service.module.system.impl.service.syncService.impl.method.syncServiceAccountOverServers;

import com.omgservers.model.dto.system.SyncServiceAccountOverServersRequest;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.service.module.system.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncServiceAccountOverServersMethodImpl implements SyncServiceAccountOverServersMethod {

    final GetInternalModuleClientOperation getInternalModuleClientOperation;

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountOverServersRequest request) {
        final var servers = request.getServers();
        final var serviceAccount = request.getServiceAccount();
        return Multi.createFrom().iterable(servers)
                .onItem().transform(getInternalModuleClientOperation::getClient)
                .onItem().transformToUniAndConcatenate(client -> {
                    final var syncServiceAccountInternalRequest = new SyncServiceAccountRequest(serviceAccount);
                    return client.syncServiceAccount(syncServiceAccountInternalRequest);
                })
                .collect().asList().replaceWithVoid();
    }
}
