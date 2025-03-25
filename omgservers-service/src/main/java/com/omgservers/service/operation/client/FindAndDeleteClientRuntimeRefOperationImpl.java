package com.omgservers.service.operation.client;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefResponse;
import com.omgservers.schema.module.client.clientRuntimeRef.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.FindClientRuntimeRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class FindAndDeleteClientRuntimeRefOperationImpl implements FindAndDeleteClientRuntimeRefOperation {

    final ClientShard clientShard;

    @Override
    public Uni<Void> execute(final Long clientId,
                             final Long runtimeId) {
        return findClientRuntimeRef(clientId, runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(clientRuntimeRef -> {
                    final var clientRuntimeRefId = clientRuntimeRef.getId();
                    return deleteClientRuntimeRef(runtimeId, clientRuntimeRefId);
                })
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> findClientRuntimeRef(final Long clientId, final Long runtimeId) {
        final var request = new FindClientRuntimeRefRequest(clientId, runtimeId);
        return clientShard.getService().execute(request)
                .map(FindClientRuntimeRefResponse::getClientRuntimeRef);
    }

    Uni<Boolean> deleteClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new DeleteClientRuntimeRefRequest(clientId, id);
        return clientShard.getService().execute(request)
                .map(DeleteClientRuntimeRefResponse::getDeleted);
    }
}
