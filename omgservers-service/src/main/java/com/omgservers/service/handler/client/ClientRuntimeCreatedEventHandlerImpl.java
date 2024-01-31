package com.omgservers.service.handler.client;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientRuntimeCreatedEventBodyModel;
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
public class ClientRuntimeCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_RUNTIME_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientRuntimeCreatedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        return clientModule.getShortcutService().getClientRuntime(clientId, id)
                .flatMap(clientRuntime -> {
                    final var runtimeId = clientRuntime.getRuntimeId();

                    log.info("Client runtime was created, clientRuntime={}/{}, runtimeId={}",
                            clientId, id, runtimeId);

                    return syncRuntimeClient(runtimeId, clientId);
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncRuntimeClient(final Long runtimeId, final Long clientId) {
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId, clientId);
        return runtimeModule.getShortcutService().syncRuntimeClient(runtimeClient);
    }
}

