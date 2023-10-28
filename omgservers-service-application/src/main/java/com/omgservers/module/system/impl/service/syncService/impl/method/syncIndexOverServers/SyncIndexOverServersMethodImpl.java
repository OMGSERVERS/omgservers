package com.omgservers.module.system.impl.service.syncService.impl.method.syncIndexOverServers;

import com.omgservers.model.dto.internal.SyncIndexOverServersRequest;
import com.omgservers.model.dto.internal.SyncIndexRequest;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
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
    public Uni<Void> syncIndex(SyncIndexOverServersRequest request) {
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
