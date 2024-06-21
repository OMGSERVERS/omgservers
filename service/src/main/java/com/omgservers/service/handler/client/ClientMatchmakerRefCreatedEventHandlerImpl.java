package com.omgservers.service.handler.client;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.client.GetClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.GetClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.MatchmakerAssignmentMessageBodyModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientMatchmakerRefCreatedEventHandlerImpl implements EventHandler {

    final ClientModule clientModule;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_MATCHMAKER_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientMatchmakerRefCreatedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        return getClientMatchmakerRef(clientId, id)
                .flatMap(clientMatchmakerRef -> {
                    final var matchmakerId = clientMatchmakerRef.getMatchmakerId();

                    log.info("Client matchmaker ref was created, clientMatchmaker={}/{}, matchmakerId={}",
                            clientId, id, matchmakerId);

                    final var idempotencyKey = event.getIdempotencyKey();

                    return syncMatchmakerAssignmentMessage(clientId, matchmakerId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<ClientMatchmakerRefModel> getClientMatchmakerRef(final Long clientId, final Long id) {
        final var request = new GetClientMatchmakerRefRequest(clientId, id);
        return clientModule.getClientService().getClientMatchmakerRef(request)
                .map(GetClientMatchmakerRefResponse::getClientMatchmakerRef);
    }

    Uni<Boolean> syncMatchmakerAssignmentMessage(final Long clientId,
                                                 final Long matchmakerId,
                                                 final String idempotencyKey) {
        final var messageBody = new MatchmakerAssignmentMessageBodyModel(matchmakerId);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.MATCHMAKER_ASSIGNMENT_MESSAGE,
                messageBody,
                idempotencyKey);
        return syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", clientMessage, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}

