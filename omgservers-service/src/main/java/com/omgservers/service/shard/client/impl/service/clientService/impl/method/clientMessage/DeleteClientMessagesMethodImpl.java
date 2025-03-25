package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.module.client.clientMessage.DeleteClientMessagesRequest;
import com.omgservers.schema.module.client.clientMessage.DeleteClientMessagesResponse;
import com.omgservers.service.shard.client.impl.operation.clientMessage.DeleteClientMessagesByIdsOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteClientMessagesResponse> execute(
            final DeleteClientMessagesRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        final var ids = request.getIds();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteClientMessagesByIdsOperation
                                        .deleteClientMessagesByIds(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                clientId,
                                                ids))
                        .map(ChangeContext::getResult))
                .map(DeleteClientMessagesResponse::new);
    }
}
