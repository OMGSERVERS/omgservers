package com.omgservers.service.handler.impl.client;

import com.omgservers.schema.message.body.RuntimeAssignedMessageBodyDto;
import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.shard.client.clientMessage.SyncClientMessageResponse;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefResponse;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientRuntimeRefCreatedEventBodyModel;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientRuntimeRefCreatedEventHandlerImpl implements EventHandler {

    final RuntimeShard runtimeShard;
    final ClientShard clientShard;

    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_RUNTIME_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientRuntimeRefCreatedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getClientRuntimeRef(clientId, id)
                .flatMap(clientRuntimeRef -> {
                    log.debug("Created, {}", clientRuntimeRef);

                    final var runtimeId = clientRuntimeRef.getRuntimeId();
                    return getRuntime(runtimeId)
                            .flatMap(runtime -> createRuntimeAssignedClientMessage(runtime, clientId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> getClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new GetClientRuntimeRefRequest(clientId, id);
        return clientShard.getService().execute(request)
                .map(GetClientRuntimeRefResponse::getClientRuntimeRef);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> createRuntimeAssignedClientMessage(final RuntimeModel runtime,
                                                    final Long clientId,
                                                    final String idempotencyKey) {
        final var messageBody = new RuntimeAssignedMessageBodyDto(runtime.getId(),
                runtime.getQualifier(),
                runtime.getConfig());
        final var clientMessage = clientMessageModelFactory.create(clientId,
                messageBody,
                idempotencyKey);
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientShard.getService().executeWithIdempotency(request)
                .map(SyncClientMessageResponse::getCreated);
    }
}

