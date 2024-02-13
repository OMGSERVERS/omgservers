package com.omgservers.service.module.client.impl.service.clientService.impl.method.interchange;

import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.event.body.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.factory.MessageModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.client.impl.operation.deleteClientMessagesByIds.DeleteClientMessagesByIdsOperation;
import com.omgservers.service.module.client.impl.operation.selectActiveClientMessagesByClientId.SelectActiveClientMessagesByClientIdOperation;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InterchangeMethodImpl implements InterchangeMethod {

    final ClientModule clientModule;
    final SystemModule systemModule;

    final SelectActiveClientMessagesByClientIdOperation selectActiveClientMessagesByClientIdOperation;
    final DeleteClientMessagesByIdsOperation deleteClientMessagesByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final MessageModelFactory messageModelFactory;
    final EventModelFactory eventModelFactory;

    final PgPool pgPool;

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        log.debug("Interchange, request={}", request);

        final var fromUserId = request.getFromUserId();
        final var clientId = request.getClientId();
        final var consumedMessages = request.getConsumedMessages();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> clientModule.getShortcutService().getClient(clientId)
                        .flatMap(client -> {
                            final var shard = shardModel.shard();

                            if (client.getUserId().equals(fromUserId)) {
                                if (client.getDeleted()) {
                                    log.info("Client was already deleted, " +
                                            "skip message handling, clientId={}", clientId);
                                    return receiveMessages(shard, clientId, consumedMessages);
                                }

                                return handleMessages(clientId, request.getOutgoingMessages())
                                        .flatMap(voidItem -> receiveMessages(shard, clientId, consumedMessages));
                            } else {
                                throw new ServerSideBadRequestException("wrong clientId, " + clientId);
                            }
                        })
                        .map(InterchangeResponse::new)
                );
    }

    Uni<Void> handleMessages(final Long clientId, final List<MessageModel> messages) {
        return Multi.createFrom().iterable(messages)
                .onItem().transformToUniAndConcatenate(message -> {
                    final var eventBody = switch (message.getQualifier()) {
                        case CLIENT_MESSAGE -> new ClientMessageReceivedEventBodyModel(clientId,
                                message);
                        case MATCHMAKER_MESSAGE -> new MatchmakerMessageReceivedEventBodyModel(clientId,
                                message);
                        default -> throw new ServerSideBadRequestException(
                                "unsupported message has been received, " + message.getQualifier());
                    };

                    final var eventModel = eventModelFactory.create(eventBody);
                    final var syncEventRequest = new SyncEventRequest(eventModel);
                    return systemModule.getEventService().syncEvent(syncEventRequest)
                            .map(SyncEventResponse::getCreated)
                            .replaceWith(true);
                })
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<List<MessageModel>> receiveMessages(final int shard,
                                            final Long clientId,
                                            final List<Long> consumedMessages) {
        return changeWithContextOperation.<List<MessageModel>>changeWithContext((changeContext, sqlConnection) ->
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
                                                clientId)
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
