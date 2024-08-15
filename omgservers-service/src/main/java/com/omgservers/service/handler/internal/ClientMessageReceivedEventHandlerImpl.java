package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.ClientMessageReceivedEventBodyModel;
import com.omgservers.schema.model.message.body.ClientOutgoingMessageBodyModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.runtime.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientMessageReceivedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final UserModule userModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_MESSAGE_RECEIVED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientMessageReceivedEventBodyModel) event.getBody();

        final var clientId = body.getClientId();
        final var message = body.getMessage();

        if (message.getBody() instanceof final ClientOutgoingMessageBodyModel messageBody) {
            return selectClientRuntimeRef(clientId)
                    .flatMap(clientRuntimeRef -> syncHandleMessageRuntimeCommand(clientRuntimeRef,
                            messageBody.getData(), event.getIdempotencyKey()))
                    .replaceWithVoid();
        } else {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_CLIENT_MESSAGE_BODY_TYPE,
                    "body type mismatch, " + message.getBody().getClass().getSimpleName());
        }
    }

    Uni<ClientRuntimeRefModel> selectClientRuntimeRef(final Long clientId) {
        return viewClientRuntimeRefs(clientId)
                .map(clientRuntimeRefs -> {
                    if (clientRuntimeRefs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                String.format("runtime was not selected, clientId=%d", clientId));
                    } else {
                        return clientRuntimeRefs.stream()
                                .max(Comparator.comparing(ClientRuntimeRefModel::getId))
                                .get();
                    }
                });
    }

    Uni<List<ClientRuntimeRefModel>> viewClientRuntimeRefs(final Long clientId) {
        final var request = new ViewClientRuntimeRefsRequest(clientId);
        return clientModule.getClientService().viewClientRuntimeRefs(request)
                .map(ViewClientRuntimeRefsResponse::getClientRuntimeRefs);
    }

    Uni<Boolean> syncHandleMessageRuntimeCommand(final ClientRuntimeRefModel clientRuntimeRef,
                                                 final Object message,
                                                 final String idempotencyKey) {
        final var clientId = clientRuntimeRef.getClientId();
        final var runtimeId = clientRuntimeRef.getRuntimeId();
        final var runtimeCommandBody = new HandleMessageRuntimeCommandBodyModel(clientId, message);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody, idempotencyKey);
        return syncClientCommand(clientId, runtimeCommand);
    }

    Uni<Boolean> syncClientCommand(final Long clientId,
                                   final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncClientCommandRequest(clientId, runtimeCommand);
        return runtimeModule.getRuntimeService().syncClientCommand(request)
                .map(SyncClientCommandResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeCommand, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
