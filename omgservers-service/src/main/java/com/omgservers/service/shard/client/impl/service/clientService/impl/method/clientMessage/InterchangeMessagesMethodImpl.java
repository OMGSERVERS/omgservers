package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.message.body.MessageProducedMessageBodyDto;
import com.omgservers.schema.message.body.MessageReceivedMessageBodyDto;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.client.client.GetClientRequest;
import com.omgservers.schema.module.client.client.GetClientResponse;
import com.omgservers.schema.module.client.clientMessage.InterchangeMessagesRequest;
import com.omgservers.schema.module.client.clientMessage.InterchangeMessagesResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.message.MessageModelFactory;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.operation.client.SelectClientRuntimeOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.service.cache.CacheService;
import com.omgservers.service.service.cache.dto.SetClientLastActivityRequest;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.client.impl.operation.clientMessage.DeleteClientMessagesByIdsOperation;
import com.omgservers.service.shard.client.impl.operation.clientMessage.SelectActiveClientMessagesByClientIdOperation;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final RuntimeShard runtimeShard;
    final ClientShard clientShard;

    final CacheService cacheService;

    final SelectActiveClientMessagesByClientIdOperation selectActiveClientMessagesByClientIdOperation;
    final DeleteClientMessagesByIdsOperation deleteClientMessagesByIdsOperation;
    final SelectClientRuntimeOperation selectClientRuntimeOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final RuntimeMessageModelFactory runtimeMessageModelFactory;
    final MessageModelFactory messageModelFactory;

    @Override
    public Uni<InterchangeMessagesResponse> execute(final InterchangeMessagesRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        final var consumedMessages = request.getConsumedMessages();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> getClient(clientId)
                        .flatMap(client -> {
                            final var shard = shardModel.shard();
                            if (client.getDeleted()) {
                                // If client was deleted then only receiving is available
                                return receiveMessages(shard, clientId, consumedMessages);
                            } else {
                                return setClientLastActivity(clientId)
                                        .flatMap(voidItem -> {
                                            final var outgoingMessages = request.getOutgoingMessages();
                                            return deliverMessages(clientId, outgoingMessages);
                                        })
                                        .flatMap(voidItem2 -> receiveMessages(shard, clientId, consumedMessages));
                            }
                        }))
                .map(InterchangeMessagesResponse::new);
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Void> setClientLastActivity(final Long clientId) {
        final var lastActivity = Instant.now();
        final var request = new SetClientLastActivityRequest(clientId, lastActivity);
        return cacheService.execute(request)
                .replaceWithVoid();
    }

    Uni<Void> deliverMessages(final Long clientId, final List<MessageModel> messages) {
        if (messages.isEmpty()) {
            return Uni.createFrom().voidItem();
        } else {
            return selectClientRuntimeOperation.execute(clientId)
                    .flatMap(runtimeId -> Multi.createFrom().iterable(messages)
                            .onItem().transformToUniAndConcatenate(message -> {
                                if (message.getBody() instanceof final MessageProducedMessageBodyDto messageBody) {
                                    return createMessageReceivedRuntimeMessage(clientId,
                                            runtimeId,
                                            messageBody.getMessage());
                                } else {
                                    throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_MESSAGE,
                                            "wrong message, clazz=" + message.getBody().getClass().getSimpleName());
                                }
                            })
                            .collect().asList()
                    )
                    .replaceWithVoid();
        }
    }

    Uni<Boolean> createMessageReceivedRuntimeMessage(final Long clientId,
                                                     final Long runtimeId,
                                                     final Object message) {
        final var messageBody = new MessageReceivedMessageBodyDto(clientId, message);
        final var runtimeMessage = runtimeMessageModelFactory.create(runtimeId, messageBody);
        final var request = new SyncRuntimeMessageRequest(runtimeMessage);
        return runtimeShard.getService().execute(request)
                .map(SyncRuntimeMessageResponse::getCreated);
    }

    Uni<List<ClientMessageModel>> receiveMessages(final int shard,
                                                  final Long clientId,
                                                  final List<Long> consumedMessages) {
        return changeWithContextOperation.<List<ClientMessageModel>>changeWithContext((changeContext, sqlConnection) ->
                        deleteClientMessagesByIdsOperation.deleteClientMessagesByIds(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        clientId,
                                        consumedMessages)
                                .flatMap(deleted -> selectActiveClientMessagesByClientIdOperation
                                        .selectActiveClientMessagesByClientId(
                                                sqlConnection,
                                                shard,
                                                clientId))
                )
                .map(ChangeContext::getResult);
    }
}
