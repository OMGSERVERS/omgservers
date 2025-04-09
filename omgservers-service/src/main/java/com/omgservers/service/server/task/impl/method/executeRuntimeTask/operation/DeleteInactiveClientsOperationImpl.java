package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.module.client.client.DeleteClientRequest;
import com.omgservers.schema.module.client.client.DeleteClientResponse;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteInactiveClientsOperationImpl implements DeleteInactiveClientsOperation {

    final ClientShard clientShard;

    @Override
    public Uni<Void> execute(final List<Long> inactiveClients) {
        return Multi.createFrom().iterable(inactiveClients)
                .onItem().transformToUniAndConcatenate(inactiveClientId ->
                        deleteClient(inactiveClientId)
                                .onFailure()
                                .recoverWithItem(t -> {
                                    log.error("Failed to delete, clientId={}, {}:{}",
                                            inactiveClientId,
                                            t.getClass().getSimpleName(),
                                            t.getMessage());
                                    return Boolean.FALSE;
                                }))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(DeleteClientResponse::getDeleted);
    }
}
