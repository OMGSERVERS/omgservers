package com.omgservers.base.impl.service.syncInternalService.impl.method.syncIndexOverServersInternalMethod;

import com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import com.omgservers.dto.internalModule.SyncIndexOverServersInternalRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexOverServersInternalMethodImpl implements SyncIndexOverServersInternalMethod {

    final GetInternalsServiceApiClientOperation getInternalsServiceApiClientOperation;

    @Override
    public Uni<Void> syncIndex(SyncIndexOverServersInternalRequest request) {
        SyncIndexOverServersInternalRequest.validateSyncIndexOverServersInternalRequest(request);

        final var servers = request.getServers();
        final var index = request.getIndex();
        return Multi.createFrom().iterable(servers)
                .onItem().transform(getInternalsServiceApiClientOperation::getClient)
                .onItem().transformToUniAndConcatenate(client -> {
                    final var syncIndexInternalRequest = new SyncIndexHelpRequest(index);
                    return client.syncIndex(syncIndexInternalRequest);
                })
                .collect().asList().replaceWithVoid();
    }
}
