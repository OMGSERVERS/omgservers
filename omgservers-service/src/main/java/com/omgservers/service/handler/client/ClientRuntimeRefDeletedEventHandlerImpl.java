package com.omgservers.service.handler.client;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.ClientRuntimeRefModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientRuntimeRefDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;
    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_RUNTIME_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientRuntimeRefDeletedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        return getClientRuntimeRef(clientId, id)
                .flatMap(clientRuntimeRef -> {
                    final var runtimeId = clientRuntimeRef.getRuntimeId();

                    log.info("Client runtime ref was deleted, id={}, clientId={}, runtimeId={}",
                            clientRuntimeRef.getId(), clientId, runtimeId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> getClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new GetClientRuntimeRefRequest(clientId, id);
        return clientModule.getClientService().getClientRuntimeRef(request)
                .map(GetClientRuntimeRefResponse::getClientRuntimeRef);
    }
}

