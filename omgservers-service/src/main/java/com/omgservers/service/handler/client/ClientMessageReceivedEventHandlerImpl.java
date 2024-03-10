package com.omgservers.service.handler.client;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.message.body.ClientMessageBodyModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
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

        if (message.getBody() instanceof final ClientMessageBodyModel messageBody) {
            return selectClientRuntimeRef(clientId)
                    .flatMap(clientRuntimeRef -> syncHandleMessageRuntimeCommand(clientRuntimeRef,
                            messageBody.getData()))
                    .replaceWithVoid();
        } else {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.CLIENT_MESSAGE_BODY_TYPE_MISMATCH,
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
                                                 final Object message) {
        final var clientId = clientRuntimeRef.getClientId();
        final var runtimeId = clientRuntimeRef.getRuntimeId();
        final var runtimeCommandBody = new HandleMessageRuntimeCommandBodyModel(clientId, message);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return syncClientCommand(clientId, runtimeCommand);
    }

    Uni<Boolean> syncClientCommand(final Long clientId, final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncClientCommandRequest(clientId, runtimeCommand);
        return runtimeModule.getRuntimeService().syncClientCommand(request)
                .map(SyncClientCommandResponse::getCreated);
    }
}
