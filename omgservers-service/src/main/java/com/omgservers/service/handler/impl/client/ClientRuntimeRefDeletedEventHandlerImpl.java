package com.omgservers.service.handler.impl.client;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.module.client.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.GetClientRuntimeRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
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

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_RUNTIME_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (ClientRuntimeRefDeletedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        return getClientRuntimeRef(clientId, id)
                .flatMap(clientRuntimeRef -> {
                    log.debug("Deleted, {}", clientRuntimeRef);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> getClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new GetClientRuntimeRefRequest(clientId, id);
        return clientModule.getService().getClientRuntimeRef(request)
                .map(GetClientRuntimeRefResponse::getClientRuntimeRef);
    }
}

