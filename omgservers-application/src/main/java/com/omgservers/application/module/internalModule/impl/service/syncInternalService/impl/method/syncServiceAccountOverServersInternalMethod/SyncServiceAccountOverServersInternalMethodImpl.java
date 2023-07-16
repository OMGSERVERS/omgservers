package com.omgservers.application.module.internalModule.impl.service.syncInternalService.impl.method.syncServiceAccountOverServersInternalMethod;

import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.request.SyncServiceAccountOverServersInternalRequest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncServiceAccountOverServersInternalMethodImpl implements SyncServiceAccountOverServersInternalMethod {

    final GetInternalsServiceApiClientOperation getInternalsServiceApiClientOperation;

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountOverServersInternalRequest request) {
        SyncServiceAccountOverServersInternalRequest.validateSyncServiceAccountOverServersInternalRequest(request);

        final var servers = request.getServers();
        final var serviceAccount = request.getServiceAccount();
        return Multi.createFrom().iterable(servers)
                .onItem().transform(getInternalsServiceApiClientOperation::getClient)
                .onItem().transformToUniAndConcatenate(client -> {
                    final var syncServiceAccountInternalRequest = new SyncServiceAccountHelpRequest(serviceAccount);
                    return client.syncServiceAccount(syncServiceAccountInternalRequest);
                })
                .collect().asList().replaceWithVoid();
    }
}
