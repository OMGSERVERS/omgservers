package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.DeleteClientRuntimeRefResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.DeleteClientRuntimeRefOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteClientRuntimeRefMethodImpl implements DeleteClientRuntimeRefMethod {

    final DeleteClientRuntimeRefOperation deleteClientRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteClientRuntimeRefResponse> execute(final ShardModel shardModel,
                                                       final DeleteClientRuntimeRefRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteClientRuntimeRefOperation
                                .deleteClientRuntimeRef(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        clientId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteClientRuntimeRefResponse::new);
    }
}
