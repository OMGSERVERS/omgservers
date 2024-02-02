package com.omgservers.service.handler.client;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientRuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.ClientRuntimeModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientRuntimeDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;
    final ClientRuntimeModelFactory clientRuntimeModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_RUNTIME_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientRuntimeDeletedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        return clientModule.getShortcutService().getClientRuntime(clientId, id)
                .flatMap(clientRuntime -> {
                    final var runtimeId = clientRuntime.getRuntimeId();

                    log.info("Client runtime was deleted, clientRuntime={}/{}, runtimeId={}",
                            clientId, id, runtimeId);

                    return runtimeModule.getShortcutService().findAndDeleteRuntimeClient(runtimeId, clientId);
                })
                .replaceWith(true);
    }
}

