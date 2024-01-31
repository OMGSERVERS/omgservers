package com.omgservers.service.module.client.impl.service.clientService.impl.method.handleMessage;

import com.omgservers.model.dto.client.HandleMessageRequest;
import com.omgservers.model.dto.client.HandleMessageResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.event.body.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleMessageMethodImpl implements HandleMessageMethod {

    final ClientModule clientModule;
    final SystemModule systemModule;

    final CheckShardOperation checkShardOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<HandleMessageResponse> handleMessage(final HandleMessageRequest request) {
        log.debug("Handle message, request={}", request);

        final var fromUserId = request.getFromUserId();
        final var clientId = request.getClientId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> clientModule.getShortcutService().getClient(clientId)
                        .flatMap(client -> {
                            if (client.getUserId().equals(fromUserId)) {
                                final var message = request.getMessage();
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
                                        .map(SyncEventResponse::getCreated);
                            } else {
                                throw new ServerSideBadRequestException("wrong clientId, " + clientId);
                            }
                        })
                        .replaceWith(true)
                        .map(HandleMessageResponse::new)
                );
    }
}
