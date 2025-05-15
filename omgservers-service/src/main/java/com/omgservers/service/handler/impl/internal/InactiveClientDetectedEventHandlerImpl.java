package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.message.body.ClientDeletedMessageBodyDto;
import com.omgservers.schema.message.body.DisconnectionReasonEnum;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.schema.shard.client.client.DeleteClientRequest;
import com.omgservers.schema.shard.client.client.DeleteClientResponse;
import com.omgservers.schema.shard.client.client.GetClientRequest;
import com.omgservers.schema.shard.client.client.GetClientResponse;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.InactiveClientDetectedEventBodyModel;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.client.ClientShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InactiveClientDetectedEventHandlerImpl implements EventHandler {

    final ClientShard clientShard;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INACTIVE_CLIENT_DETECTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (InactiveClientDetectedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return getClient(clientId)
                .flatMap(client -> {
                    if (client.getDeleted()) {
                        log.debug("The client \"{}\" was already deleted, skip operation", client);
                        return Uni.createFrom().item(Boolean.FALSE);
                    } else {
                        log.debug("Client \"{}\" of deployment \"{}\" was marked inactive after no activity",
                                client.getId(), client.getDeploymentId());

                        return syncDisconnectionMessage(clientId, event.getIdempotencyKey())
                                .flatMap(created -> deleteClient(clientId));
                    }
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> syncDisconnectionMessage(final Long clientId, final String idempotencyKey) {
        final var messageBody = new ClientDeletedMessageBodyDto(DisconnectionReasonEnum.CLIENT_INACTIVITY);
        final var disconnectionMessage = clientMessageModelFactory.create(clientId,
                messageBody,
                idempotencyKey);
        return syncClientMessage(disconnectionMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientShard.getService().executeWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Boolean> deleteClient(final Long clientId) {
        final var request = new DeleteClientRequest(clientId);
        return clientShard.getService().execute(request)
                .map(DeleteClientResponse::getDeleted);
    }
}
