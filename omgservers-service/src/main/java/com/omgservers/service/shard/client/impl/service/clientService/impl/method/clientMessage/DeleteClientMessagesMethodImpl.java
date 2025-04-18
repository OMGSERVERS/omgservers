package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientMessage.DeleteClientMessagesRequest;
import com.omgservers.schema.shard.client.clientMessage.DeleteClientMessagesResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.client.impl.operation.clientMessage.DeleteClientMessagesByIdsOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteClientMessagesMethodImpl implements DeleteClientMessagesMethod {

    final DeleteClientMessagesByIdsOperation deleteClientMessagesByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteClientMessagesResponse> execute(final ShardModel shardModel,
                                                     final DeleteClientMessagesRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        final var ids = request.getIds();

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteClientMessagesByIdsOperation.deleteClientMessagesByIds(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                clientId,
                                ids))
                .map(ChangeContext::getResult)
                .map(DeleteClientMessagesResponse::new);
    }
}
