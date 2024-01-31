package com.omgservers.service.module.client.impl.service.clientService.impl.method.receiveMessages;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.ReceiveMessagesRequest;
import com.omgservers.model.dto.client.ReceiveMessagesResponse;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.client.impl.operation.deleteClientMessagesByIds.DeleteClientMessagesByIdsOperation;
import com.omgservers.service.module.client.impl.operation.selectActiveClientMessagesByClientId.SelectActiveClientMessagesByClientIdOperation;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ReceiveMessagesMethodImpl implements ReceiveMessagesMethod {

    final ClientModule clientModule;
    final SystemModule systemModule;

    final SelectActiveClientMessagesByClientIdOperation selectActiveClientMessagesByClientIdOperation;
    final DeleteClientMessagesByIdsOperation deleteClientMessagesByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<ReceiveMessagesResponse> receiveMessages(final ReceiveMessagesRequest request) {
        log.debug("Receive messages, request={}", request);

        final var forUserId = request.getForUserId();
        final var clientId = request.getClientId();
        final var consumedMessages = request.getConsumedMessages();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> clientModule.getShortcutService().getClient(clientId)
                        .flatMap(client -> {
                            if (client.getUserId().equals(forUserId)) {
                                return receiveMessages(shard, client, consumedMessages)
                                        .map(ReceiveMessagesResponse::new);
                            } else {
                                throw new ServerSideBadRequestException("wrong clientId, " + clientId);
                            }
                        })
                );
    }

    Uni<List<MessageModel>> receiveMessages(final ShardModel shardModel,
                                            final ClientModel client,
                                            final List<Long> consumedMessages) {
        final var clientId = client.getId();
        return changeWithContextOperation.<List<MessageModel>>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteClientMessagesByIdsOperation
                                        .deleteClientMessagesByIds(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                clientId,
                                                consumedMessages)
                                        .flatMap(deleted -> selectActiveClientMessagesByClientIdOperation
                                                .selectActiveClientMessagesByClientId(sqlConnection,
                                                        shardModel.shard(),
                                                        client.getId())
                                                .map(clientMessages -> clientMessages.stream()
                                                        .map(clientMessage ->
                                                                messageModelFactory.create(clientMessage.getId(),
                                                                        clientMessage.getQualifier(),
                                                                        clientMessage.getBody()))
                                                        .toList()))
                )
                .map(ChangeContext::getResult);
    }
}
