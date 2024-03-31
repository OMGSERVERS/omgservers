package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.deleteClientMessages;

import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.service.module.client.impl.operation.clientMessage.deleteClientMessagesByIds.DeleteClientMessagesByIdsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            final DeleteClientMessagesRequest request) {
        log.debug("Delete client messages, request={}", request);

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
