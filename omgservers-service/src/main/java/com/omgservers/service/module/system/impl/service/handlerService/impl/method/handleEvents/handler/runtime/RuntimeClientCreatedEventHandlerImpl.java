package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.runtime;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeClientCreatedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeClientCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeClientCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return runtimeModule.getShortcutService().getRuntime(runtimeId)
                .flatMap(runtime -> runtimeModule.getShortcutService().getRuntimeClient(runtimeId, id)
                        .flatMap(runtimeClient -> {
                            final var clientId = runtimeClient.getClientId();

                            log.info("Runtime client was created, runtimeClient={}/{}, clientId={}",
                                    runtimeClient.getRuntimeId(), runtimeClient.getId(), clientId);

                            return syncAssignmentMessage(runtime, clientId)
                                    .flatMap(created -> syncAddClientRuntimeCommand(runtime, clientId));
                        })
                )
                .replaceWithVoid();
    }

    Uni<Boolean> syncAssignmentMessage(final RuntimeModel runtime,
                                       final Long clientId) {
        final var messageBody = new AssignmentMessageBodyModel(runtime.getId(),
                runtime.getQualifier(),
                runtime.getConfig());
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                messageBody);
        return clientModule.getShortcutService().syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncAddClientRuntimeCommand(final RuntimeModel runtime,
                                             final Long clientId) {
        final var runtimeId = runtime.getId();
        final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return runtimeModule.getShortcutService().syncRuntimeCommand(runtimeCommand);
    }
}
