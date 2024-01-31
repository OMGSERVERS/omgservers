package com.omgservers.service.handler.runtime;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeClientDeletedEventBodyModel;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
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
public class RuntimeClientDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CLIENT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeClientDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return runtimeModule.getShortcutService().getRuntimeClient(runtimeId, id)
                .flatMap(runtimeClient -> {
                    final var clientId = runtimeClient.getClientId();

                    log.info("Runtime client was deleted, runtimeClient={}/{}, clientId={}",
                            runtimeClient.getRuntimeId(), runtimeClient.getId(), clientId);

                    return runtimeModule.getShortcutService().getRuntime(runtimeId)
                            .flatMap(runtime -> {
                                if (runtime.getDeleted()) {
                                    log.warn("Runtime was already deleted, " +
                                            "DeleteClientRuntimeCommand won't be created, runtimeId={}", runtimeId);
                                    return Uni.createFrom().voidItem();
                                }

                                return syncDeleteClientRuntimeCommand(runtimeId, clientId);
                            });
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncDeleteClientRuntimeCommand(final Long runtimeId,
                                                final Long clientId) {
        final var runtimeCommandBody = new DeleteClientRuntimeCommandBodyModel(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return runtimeModule.getShortcutService().syncRuntimeCommand(runtimeCommand);
    }
}
