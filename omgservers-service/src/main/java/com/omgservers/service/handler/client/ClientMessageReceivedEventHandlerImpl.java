package com.omgservers.service.handler.client;

import com.omgservers.model.clientRuntime.ClientRuntimeModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientMessageReceivedEventBodyModel;
import com.omgservers.model.message.body.ClientMessageBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientMessageReceivedEventBodyModel) event.getBody();

        final var clientId = body.getClientId();
        final var message = body.getMessage();

        if (message.getBody() instanceof final ClientMessageBodyModel messageBody) {
            return clientModule.getShortcutService().selectLatestClientRuntime(clientId)
                    .flatMap(clientRuntime -> syncHandleMessageRuntimeCommand(clientRuntime,
                            messageBody.getData())
                    );
        } else {
            throw new ServerSideBadRequestException("message body type mismatch, " +
                    message.getBody().getClass().getSimpleName());
        }
    }

    Uni<Boolean> syncHandleMessageRuntimeCommand(final ClientRuntimeModel clientRuntime,
                                                 final Object message) {
        final var clientId = clientRuntime.getClientId();
        final var runtimeId = clientRuntime.getRuntimeId();
        final var runtimeCommandBody = new HandleMessageRuntimeCommandBodyModel(clientId, message);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return runtimeModule.getShortcutService().syncRuntimeCommand(runtimeCommand);
    }
}
