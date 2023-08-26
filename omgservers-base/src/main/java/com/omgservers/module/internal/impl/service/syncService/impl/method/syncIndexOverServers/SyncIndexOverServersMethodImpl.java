package com.omgservers.module.internal.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncIndexOverServersInternalRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexOverServersMethodImpl implements SyncIndexOverServersMethod {

    final GetInternalModuleClientOperation getInternalModuleClientOperation;

    @Override
    public Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request) {
        SyncIndexOverServersInternalRequest.validateSyncIndexOverServersInternalRequest(request);

        final var servers = request.getServers();
        final var index = request.getIndex();
        return Multi.createFrom().iterable(servers)
                .onItem().transform(getInternalModuleClientOperation::getClient)
                .onItem().transformToUniAndConcatenate(client -> {
                    final var syncIndexInternalRequest = new SyncIndexRequest(index);
                    return client.syncIndex(syncIndexInternalRequest);
                })
                .collect().asList().replaceWithVoid();
    }
}
